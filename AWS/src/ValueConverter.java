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

    public static double inchesHgToHectoPascal(short rawValue) {
        return (double)rawValue * 0.029530d;
    }

    public static String humidity(short rawValue) {
        return rawValue + "%";
    }

    public static double windSpeed(short rawValue) {
        return rawValue * 1.609344d;
    }

    public static String sunRise(short rawValue) {
        String tijd = Integer.toString(rawValue);
        if (tijd.length() == 4) {
            String output = tijd.charAt(0) + tijd.charAt(1) +  ":" + tijd.charAt(2) + tijd.charAt(3);
        } else {
            String output = "0" + tijd.charAt(0) +  ":" + tijd.charAt(1) + tijd.charAt(2);
        }
        return tijd;
    }

    public static String sunSet(short rawValue) {
        String tijd = Integer.toString(rawValue);
        if (tijd.length() == 4) {
            String output = tijd.charAt(0) + tijd.charAt(1) +  ":" + tijd.charAt(2) + tijd.charAt(3);
        } else {
            String output = "0" + tijd.charAt(0) +  ":" + tijd.charAt(1) + tijd.charAt(2);
        }
        return tijd;
    }
}
