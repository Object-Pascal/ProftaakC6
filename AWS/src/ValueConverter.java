public class ValueConverter {
    /*
    This function converts the measured temp in fahrenheit to celsius
     */
    public static double temperature(short tempInFahrenheits) {
        return ((tempInFahrenheits / (double)10) - 32) / 1.8;
    }

    /*
    This function converts the measured RainRate in to mm/hour
     */
    public static double rainMeter(short rawValue) {
        return ((rawValue / (double)100) * 25.4);
    }

    public static double uvIndex(short rawValue) {
        return rawValue / (double)10;
    }

    public static String windDirection(short rawValue) {
        return "" + rawValue +"°";
    }
}