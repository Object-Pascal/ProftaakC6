import java.security.MessageDigest;
import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class to contain a period of time
 *
 * @author Johan Talboom
 * @version 2.0
 */
public class Period {
	private LocalDate beginPeriod;
	private LocalDate endPeriod;

	public static void main(String[] args) {
	Period testperiod = new Period();
	testperiod.setStart(2017,12,1);
	testperiod.setEnd(2017,12,10);
	ArrayList<Double> insidetemp = testperiod.getInsideTemperatures();
	ArrayList<Double> outsidetemp = testperiod.getOutsideTemperatures();
		System.out.println(testperiod.tempOverlap(insidetemp,outsidetemp));
	}

	/**
	 * default constructor, sets the period to today
	 */
	public Period() {
		beginPeriod = LocalDate.now();
		endPeriod = LocalDate.now();
	}

	public Period(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
	}

	public Period(LocalDate beginPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = LocalDate.now();
	}

	public Period(int days) {
		this.beginPeriod = LocalDate.now().minus(java.time.Period.ofDays(days));
		this.endPeriod = LocalDate.now();
	}

	/**
	 * Simple setter for start of period
	 */
	public void setStart(int year, int month, int day) {
		beginPeriod = LocalDate.of(year, month, day);
	}

	/**
	 * simple setter for end of period
	 */
	public void setEnd(int year, int month, int day) {
		endPeriod = LocalDate.of(year, month, day);
	}

	/**
	 * alternative setter for start of period
	 *
	 * @param beginPeriod
	 */
	public void setStart(LocalDate beginPeriod) {
		this.beginPeriod = beginPeriod;
	}

	/**
	 * alternative setter for end of period
	 *
	 * @param endPeriod
	 */
	public void setEnd(LocalDate endPeriod) {
		this.endPeriod = endPeriod;
	}

	/**
	 * calculates the number of days in the period
	 */
	public long numberOfDays() {
		return ChronoUnit.DAYS.between(beginPeriod, endPeriod);
	}


	/**
	 * gets all raw measurements of this period from the database
	 * @return a list of raw measurements
	 */
	public ArrayList<RawMeasurement> getRawMeasurements() {
		return DatabaseConnection.getMeasurementsBetween(LocalDateTime.of(beginPeriod, LocalTime.of(0, 1)), LocalDateTime.of(endPeriod, LocalTime.of(23, 59)));
	}

	/**
	 * Builds an ArrayList of measurements. This method also filters out any 'bad' measurements
	 * @return a filtered list of measurements
	 */
	public ArrayList<Measurement> getMeasurements() {
		ArrayList<Measurement> measurements = new ArrayList<>();
		ArrayList<RawMeasurement> rawMeasurements = getRawMeasurements();
		for (RawMeasurement rawMeasurement : rawMeasurements) {
			Measurement measurement = new Measurement(rawMeasurement);
			if(measurement.isValid()) {
				measurements.add(measurement);
			}
		}
		return measurements;
	}


	/**
	 * todo
	 * @return
	 */
	public double getAverageOutsideTemperature()
	{
		ArrayList<Measurement> measurements = getMeasurements();

		//calculate average outside temperature and return it
		return measurements.get(0).getOutsideTemperature();
	}

	/**
	 * Todo
	 */
	public ArrayList<Period> hasHeatWave() {
		ArrayList<Measurement> measurements = getMeasurements();
		return null;
	}

	/**
	 * Todo
	 */
	public Period longestDraught() {
		return new Period();
	}

	/**
	 * Todo more methods
	 */

	public Period getLongestConnectedSummerDays() {
		ArrayList<ArrayList<Measurement>> periodicMeasurements = getMeasurementsPerDay();
		ArrayList<Boolean> isSummerDay = new ArrayList<>();
		for (int i = 0; i < periodicMeasurements.size(); i++) {
			ArrayList<Measurement> dailyMeasurements = periodicMeasurements.get(i);
			double highestTemperature = 0.0;
			for (Measurement m: dailyMeasurements) {
				double temperature = m.getOutsideTemperature();
				if (highestTemperature <= temperature)
					highestTemperature = temperature;
			}

			isSummerDay.add(highestTemperature >= 25.0);
		}

		ArrayList<ArrayList<LocalDateTime>> connectedMeasurementsDates = new ArrayList<>();
		ArrayList<LocalDateTime> currentCollection = new ArrayList<>();
		for (int i = 0; i < isSummerDay.size(); i++) {
			while(isSummerDay.get(i)) {
				currentCollection.add(periodicMeasurements.get(i).get(0).getDateStamp());
				i++;
			}

			connectedMeasurementsDates.add(currentCollection);
			currentCollection = new ArrayList<>();
		}

		ArrayList<LocalDateTime> longestPeriod = connectedMeasurementsDates.get(0);
		for (int i = 1; i < connectedMeasurementsDates.size(); i++) {
			if (longestPeriod.size() < connectedMeasurementsDates.get(i).size())
				longestPeriod = connectedMeasurementsDates.get(i);
		}

		return new Period(longestPeriod.get(0).toLocalDate(), longestPeriod.get(longestPeriod.size() - 1).toLocalDate());
	}

	public ArrayList<Double> getInsideTemperatures(){
		ArrayList<RawMeasurement> measurements = getRawMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (RawMeasurement x : measurements){
			getallen.add((double)x.getInsideTemp());
		}
		return getallen;
	}

	public ArrayList<ArrayList<Measurement>> getMeasurementsPerDay() {
		ArrayList<Measurement> allMeasurements = getMeasurements();
		ArrayList<ArrayList<Measurement>> finalCollection = new ArrayList<>();
		ArrayList<Measurement> dailyMeasurements = new ArrayList<>();
		Measurement lastMeasurement = allMeasurements.get(0);
		for (Measurement m: allMeasurements) {
			if (lastMeasurement.isSameDay(m))
				dailyMeasurements.add(m);
			else {
				ArrayList<Measurement> clonedCollection = new ArrayList<>(dailyMeasurements.size());
				for (Measurement toAddMeasurement : dailyMeasurements) {
					try {
						clonedCollection.add(toAddMeasurement.clone());
					} catch (Exception e) {

					}
				}
				finalCollection.add(clonedCollection);
				lastMeasurement = m;
				dailyMeasurements.clear();
				dailyMeasurements.add(m);
			}
		}
		return finalCollection;
	}

	public double getMaxInsideTemp(){
		return max(getInsideTemperatures());
	}

	public double getMinInsideTemp(){
		return  min((getInsideTemperatures()));
	}

	public double getGemiddeldeInsideTemp(){
		return gemiddelde(getInsideTemperatures());
	}

	public double getModusInsideTemp(){
		return modus(getInsideTemperatures());
	}

	public double getMedianInsideTemp(){
		return median(getInsideTemperatures());
	}

	public double getStandaardafwijkingInsideTemp(){
		return standaardafwijking(getInsideTemperatures());
	}

	public ArrayList<Double> getOutsideTemperatures(){
		ArrayList<Measurement> measurements = new ArrayList<>();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (RawMeasurement raw : getRawMeasurements()) {
			measurements.add(new Measurement(raw));
		}

		for (Measurement x : measurements){

			getallen.add((double)x.getOutsideTemperature());

			getallen.add(x.getOutsideTemperature());

		}
		return getallen;
	}
	public double getMaxOutsideTemp(){
		return max(getOutsideTemperatures());
	}

	public double getMinOutsideTemp(){
		return  min((getOutsideTemperatures()));
	}

	public double getGemiddeldeOutsideTemp(){
		return gemiddelde(getOutsideTemperatures());
	}

	public double getMedianOutsideTemp(){
		return median(getOutsideTemperatures());
	}

	public double getModusOutsideTemp(){
		return modus(getOutsideTemperatures());
	}

	public double getStandaardafwijkingOutsideTemp(){
		return standaardafwijking(getOutsideTemperatures());
	}

	public ArrayList<Double> getAirpressure(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add(x.getAirpressure());
		}
		return getallen;
	}

	public double getMaxAirpressure(){
		return max(getAirpressure());
	}

	public double getMinAirpressure(){
		return  min(getAirpressure());
	}

	public double getGemiddeldeAirpressure(){
		return gemiddelde(getAirpressure());
	}

	public double getMedianAirpressure(){
		return median(getAirpressure());
	}

	public double getModusAirpressure(){
		return modus(getAirpressure());
	}

	public double getStandaardafwijkingAirpressure(){
		return standaardafwijking(getAirpressure());
	}

	public ArrayList<Double> getOutsideHumidity(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add((double)x.getOutsideHumidity());
		}
		return getallen;
	}

	public double getMaxOutsideHumidity(){
		return max(getOutsideHumidity());
	}

	public double getMinOutsideHumiditye(){
		return  min(getOutsideHumidity());
	}

	public double getGemiddeldeOutsideHumidity(){
		return gemiddelde(getOutsideHumidity());
	}

	public double getMedianOutsideHumidity(){
		return median(getOutsideHumidity());
	}

	public double getModusOutsideHumidity(){
		return modus(getOutsideHumidity());
	}

	public double getStandaardafwijkingOutsideHumidity(){
		return standaardafwijking(getOutsideHumidity());
	}

	public LocalDate getBeginPeriod() {
		return beginPeriod;
	}

	public LocalDate getEndPeriod() {
		return endPeriod;
	}

	public double max(ArrayList<Double> numbers){
	 double eerste = numbers.get(0);
		for (double x:numbers) {
			if(x > eerste){
				eerste = x;
			}
		}
		return eerste;
	}

	public double min(ArrayList<Double> numbers){
		double eerste = numbers.get(0);
		for (double x:numbers) {
			if(x < eerste){
				eerste = x;
			}
		}
		return eerste;

	}

	public double gemiddelde(ArrayList<Double> numbers){
		double som = 0;
		for (double x:numbers) {
			som = som + x;
		}
		return som/numbers.size();
	}

	public double median(ArrayList<Double> numbers){
		Collections.sort(numbers);
		if(numbers.size()%2 == 0){
			double median1 = numbers.get((numbers.size()/2));
			double median2 = numbers.get((numbers.size()/2)+1);
			return (median1+median2)/2;
		}
		else{
			return numbers.get((int)((numbers.size()/2)+0.5));
		}
	}


	public double standaardafwijking(ArrayList<Double> numbers){
		double average = gemiddelde(numbers);
		ArrayList<Double> afstandgemiddelde = new ArrayList<>();
		for (int i = 0; i <numbers.size() ; i++) {
			afstandgemiddelde.add(Math.abs(average-numbers.get(i)));
		}
		for (int i = 0; i <afstandgemiddelde.size() ; i++) {
			afstandgemiddelde.set(i,Math.pow(afstandgemiddelde.get(i),2));
		}
		average = gemiddelde(afstandgemiddelde);
		return Math.sqrt(average);
	}

	public double modus(ArrayList<Double> numbers){
		return 0.0;
	}


	public int graaddagen() {
		int graaddagen = 0;
		ArrayList<ArrayList<Measurement>> lijst = getMeasurementsPerDay();
		ArrayList<Double> buiten = new ArrayList<>();
		for (ArrayList<Measurement> measurement : lijst) {
			for (Measurement measure : measurement) {
				buiten.add(measure.getOutsideTemperature());
			}
			double gemiddeldBuiten = gemiddelde(buiten);
			int counter = 0;
			double totaal = 0;
			if (gemiddeldBuiten < 18) {
				totaal += gemiddeldBuiten;
				counter++;
			}
			graaddagen += (int)(18*counter - totaal);
			System.out.println(gemiddeldBuiten);
		}

		return graaddagen;
	}

	public int tempOverlap(ArrayList<Double> insidetemp,ArrayList<Double> outsidetemp) {
		int counter = 0;
		int size;
		if(insidetemp.size() > outsidetemp.size()){
			size = outsidetemp.size();
		}
		else{
			size = insidetemp.size();
		}

		for (int i = 1; i < size; i++) {
			if ((insidetemp.get(i - 1) < outsidetemp.get(i - 1)) && insidetemp.get(i) > outsidetemp.get(i)) {
				counter++;
			}
			if ((insidetemp.get(i - 1) > outsidetemp.get(i - 1)) && insidetemp.get(i) < outsidetemp.get(i)) {
				counter++;
			}
		}
		return counter;
	}


}

