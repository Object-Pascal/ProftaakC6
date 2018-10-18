public class PeriodTestMain {
	public static void main(String[] args){
		Period period = new Period(90); // last week

		//System.out.println("The average temperature of last week was " + period.getAverageOutsideTemperature());

		//Period longestPeriod = period.getLongestConnectedSummerDays();
		//System.out.println("" + longestPeriod.getBeginPeriod() + " to " + longestPeriod.getEndPeriod());
		//System.out.println("The average temperature of last week was " + period.getAverageOutsideTemperature());

		System.out.println(period.graaddagen());
		System.out.println(period.maxAaneengeslotenRegenval());
	}
}
