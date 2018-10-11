public class PeriodTestMain {
	public static void main(String[] args){

		Period period = new Period(5); // last week

		//System.out.println("The average temperature of last week was " + period.getAverageOutsideTemperature());

		System.out.println(period.graaddagen());
	}
}
