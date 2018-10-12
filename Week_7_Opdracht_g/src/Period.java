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
	private LocalDate beginPeriod;
	private LocalDate endPeriod;

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
			if(true) {
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
		return null;
	}

	/**
	 * Todo
	 */

	public LocalDate getEndPeriod() {
		return endPeriod;
	}

	public LocalDate getBeginPeriod() {
		return beginPeriod;
	}

	public Period longestDraught() {
		ArrayList<RawMeasurement> rawmeasurements = getRawMeasurements();
		ArrayList<ArrayList<RawMeasurement>> dayReadings = new ArrayList<>();
		ArrayList<Period> droughts = new ArrayList<>();

		LocalDate curr = beginPeriod;
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
		}

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
		Period longestDrought = null;
		for (int i = 0; i < droughts.size(); i++) {
			long preview = ChronoUnit.DAYS.between(droughts.get(i).getBeginPeriod(), droughts.get(i).getEndPeriod());
			if (preview > max) {
				max = preview;
				longestDrought = droughts.get(i);
			}
		}
		return longestDrought;
	}
}
