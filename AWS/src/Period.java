
import java.lang.reflect.Array;
import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class to contain a period of time
 *
 * @author Johan Talboom
 * @version 2.0
 */
public class Period {
	private  LocalDate beginPeriod;
	private LocalDate endPeriod;

	private ArrayList<RawMeasurement> rawMeasurements;
	private ArrayList<Measurement> measurements;

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
		if (rawMeasurements == null)
			rawMeasurements = DatabaseConnection.getMeasurementsBetween(LocalDateTime.of(beginPeriod, LocalTime.of(0, 1)), LocalDateTime.of(endPeriod, LocalTime.of(23, 59)));

		return rawMeasurements;
	}

	/**
	 * Builds an ArrayList of measurements. This method also filters out any 'bad' measurements
	 * @return a filtered list of measurements
	 */
	public ArrayList<Measurement> getMeasurements() {
		if (this.measurements == null)
		{
			ArrayList<Measurement> measurements = new ArrayList<>();
			ArrayList<RawMeasurement> rawMeasurements = getRawMeasurements();
			for (RawMeasurement rawMeasurement : rawMeasurements) {
				Measurement measurement = new Measurement(rawMeasurement);
				if(measurement.isValid()) {
					measurements.add(measurement);
				}
			}
			this.measurements = measurements;
		}

		return this.measurements;
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
	public Period longestDrought() {
		ArrayList<RawMeasurement> rawmeasurements = getRawMeasurements();
		ArrayList<ArrayList<Measurement>> dayReadings = getMeasurementsPerDay();
		ArrayList<Period> droughts = new ArrayList<>();

		/*LocalDate curr = beginPeriod;
		while (true) {
			ArrayList<RawMeasurement> currDayReadings = new ArrayList<>();
			for (int j = 0; j < rawmeasurements.size(); j++) {
				if (rawmeasurements.get(j).getDateStamp().toLocalDate().toString().equals(curr.toString())){
					currDayReadings.add(rawmeasurements.get(j));
				}
			}
			dayReadings.add(currDayReadings);
			curr = curr.plusDays(1);

			if (curr.toString().equals(endPeriod.plusDays(1).toString()))
				break;
		}*/

		Period buff = new Period(dayReadings.get(0).get(0).getDateStamp().toLocalDate(),
								 dayReadings.get(0).get(0).getDateStamp().toLocalDate().minusDays(1));
		for (int i = 0; i < dayReadings.size(); i++) {
			if (dayReadings.get(i).size() > 0) {
				if (dayReadings.get(i).get(0).getRainRate() <= 0) {
					boolean drought = true;

					for (int j = 0; j < dayReadings.get(i).size(); j++) {
						if (dayReadings.get(i).get(j).getRainRate() > 0) {
							drought = false;
							break;
						}
					}
					if (drought) {
						buff.setEnd(buff.getEndPeriod().plusDays(1));
					} else {
						droughts.add(buff);
						buff = new Period(dayReadings.get(i).get(0).getDateStamp().toLocalDate(),
										  dayReadings.get(i).get(0).getDateStamp().toLocalDate());
					}
				}
			}
		}

		long max = -1;
		Period longestDrought = new Period(0);
		for (int i = 0; i < droughts.size(); i++) {
			long preview = ChronoUnit.DAYS.between(droughts.get(i).getBeginPeriod(), droughts.get(i).getEndPeriod());
			if (preview > max) {
				max = preview;
				longestDrought = droughts.get(i);
			}
		}
		return longestDrought;
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

		if (longestPeriod.size() > 0)
			return new Period(longestPeriod.get(0).toLocalDate(), longestPeriod.get(longestPeriod.size() - 1).toLocalDate());
		else
			return new Period(0);
	}

	public ArrayList<Double> getInsideTemperatures(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add(x.getInsideTemperature());
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

	public  double getMaxInsideTemp(){
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

	public ArrayList<Double> getInsideHumidity(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add((double)x.getInsideHumidity());
		}
		return getallen;
	}

	public ArrayList<Double> getRainrate(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add((double)x.getRainRate());
		}
		return getallen;
	}

	public ArrayList<Double> getWindSpeed(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add((double)x.getWindSpeed());
		}
		return getallen;
	}

	public double getMaxWindspeed(){
		return max(getWindSpeed());
	}

	public double getMinWindSpeed(){
		return min(getWindSpeed());
	}

	public double getGemiddeldeWindSpeed(){
		return gemiddelde(getWindSpeed());
	}

	public double getMedianWindSpeed(){
		return median(getWindSpeed());
	}

	public double getModusWindSpeed(){
		return modus(getWindSpeed());
	}

	public double getStandaardafwijkingWindSpeed(){
		return standaardafwijking(getWindSpeed());
	}

	public double getMaxOutsideHumidity(){
		return max(getOutsideHumidity());
	}

	public double getMinOutsideHumidity(){
		return min(getOutsideHumidity());
	}

	public double getGemiddeldeOutsideHumidity(){
		return gemiddelde(getOutsideHumidity());
	}

	public double getMaxInsideHumidity(){
		return max(getInsideHumidity());
	}

	public double getMinInsideHumidity(){
		return min(getInsideHumidity());
	}

	public double getGemiddeldeInsideHumidity(){
		return gemiddelde(getInsideHumidity());
	}

	public double getMedianInsideHumidity(){
		return median(getInsideHumidity());
	}

	public double getModusInsideHumidity(){
		return modus(getInsideHumidity());
	}

	public double getStandaardafwijkingInsideHumidity(){
		return standaardafwijking(getInsideHumidity());
	}


	public double getMaxRainrate(){
		return max(getRainrate());
	}

	public double getMinRainrate(){
		return min(getRainrate());
	}

	public double getGemiddeldeRainrate(){
		return gemiddelde(getRainrate());
	}

	public double getMedianRainrate(){
		return median(getRainrate());
	}

	public double getModusRainrate(){
		return modus(getRainrate());
	}

	public double getStandaardafwijkingRainrate(){
		return standaardafwijking(getRainrate());
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

	public double getUVLevel () {
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add(x.getUvIndex());
		}
		return getallen.get(0);
	}

	public double getSolarRad () {
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> getallen = new ArrayList<Double>();
		for (Measurement x : measurements){
			getallen.add(x.getSolarRadiaton());
		}
		return getallen.get(0);
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

	public String windRichting() {
		RawMeasurement raw = DatabaseConnection.getMostRecentMeasurement();
		double graden =raw.getWindDir()%360;
			if (graden >= 337.5 || graden < 22.5){
			return "N";
		}else if (graden >= 22.5 && graden < 67.5){
			return "NO";
		}else if (graden >= 67.5 && graden < 112.5){
			return "O";
		}else if (graden >= 112.5 && graden < 157.5){
			return "ZO";
		}else if (graden >= 157.5 && graden < 202.5){
			return "Z";
		}else if (graden >= 202.5 && graden < 247.5){
			return "ZW";
		}else if (graden >= 247.5 && graden < 292.5){
			return "W";
		}else if (graden >= 292.5 && graden < 337.5){
			return "NW";
		}else{
			return "ERROR";
		}
	}

	public Double modus(ArrayList<Double> a)
	{
		ArrayList<Double> uniqueValues = new ArrayList<>();
		ArrayList<Integer> uniqueCount = new ArrayList<>();
		ArrayList<Double> modeList = new ArrayList<>();
		int maxCount = 0;
		for (int i = 0; i < a.size(); i++)
		{
			if (!(uniqueValues.contains(a.get(i))))
			{
				uniqueValues.add(a.get(i));
			}
		}

		for (int j = 0; j < uniqueValues.size() ; j++)
		{
			int count = 0;
			for (int k = 0; k < a.size(); k++)
			{
				//double uniqueValue = uniqueValues.get(j);
				//double comparisonValue = a.get(k);
				if (uniqueValues.get(j).equals(a.get(k)))
				{
					count++;
					if(count > maxCount)
					{
						maxCount = count;
					}
				}
			}
			uniqueCount.add(count);
		}
		for (int h = 0; h < uniqueValues.size(); h++)
		{
			if(uniqueCount.get(h) == maxCount)
			{
				double mode = uniqueValues.get(h);
				modeList.add(mode);
			}
		}
		return ((int)(Math.round(modeList.get(0)*10))/10.0);
	}

	public int graaddagen() {
		int graaddagen = 0;
		ArrayList<ArrayList<Measurement>> lijst = getMeasurementsPerDay();
		ArrayList<Double> buiten = new ArrayList<>();
		for (ArrayList<Measurement> measurement : lijst) {
			buiten.clear();
			for (Measurement measure : measurement) {
				buiten.add(measure.getOutsideTemperature());
			}
			double gemiddelde = gemiddelde(buiten);

			if (gemiddelde < 18) {
				graaddagen += (18 - gemiddelde);
			}
		}
		return graaddagen;
	}

	public double maxAaneengeslotenRegenval(){
		ArrayList<Measurement> measurements = getMeasurements();
		ArrayList<Double> rainrate = new ArrayList<Double>();
		double maxRegenval = 0;
		double huidigHoogste = 0.0;

		for (Measurement x : measurements){
			rainrate.add(x.getRainRate());
		}

		for(int i = 0; i < rainrate.size(); i++)
		{
			double regenval = (rainrate.get(i));

			double regenvalDouble = regenval;


			if(regenval > 10000) {
				//Ire?le waarde
			}
			else if(regenval == 0)
			{
				if(huidigHoogste > maxRegenval) {
					maxRegenval = huidigHoogste;
				}
				else{}
				huidigHoogste = 0;
			}
			else {
				huidigHoogste += regenvalDouble;
			}
		}
		if(huidigHoogste > maxRegenval){
			maxRegenval = huidigHoogste;
		}
		else{}

		int temp = (int)(maxRegenval * 10);
		return temp / 10;
	}

	public int tempOverlap() {
		int counter = 0;
		int size;
		if(getInsideTemperatures().size() > getOutsideTemperatures().size()){
			size = getOutsideTemperatures().size();
		}
		else{
			size = getInsideTemperatures().size();
		}
		ArrayList<Double> insidetemp = getInsideTemperatures();
		ArrayList<Double> outsidetemp = getOutsideTemperatures();

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

    public int mistdagen(){
	    int counterdag = 0;
	    int countertotaal = 0;
	    int counter = 0;
	    ArrayList<ArrayList<Measurement>> dagen = getMeasurementsPerDay();
	    for (ArrayList<Measurement> x : dagen ){
	        for (Measurement y : x){
	            if (dauwPunt(y) -  y.getOutsideTemperature() >= -2.5 && dauwPunt(y) -  y.getOutsideTemperature() <= 2.5)
	                counter++;
	                countertotaal++;
            }
            if (counter != 0){counterdag++;}
            counter = 0;
        }
	    return counterdag;
    }

	public double dauwPunt(Measurement m){
	    double a = (Math.log(m.getOutsideHumidity()/100)) / Math.log(2.718282) + (17.62 * m.getOutsideTemperature() / (243.12 + m.getOutsideTemperature()));
	    return 243.12 * a / (17.62 - a);
    }


}


