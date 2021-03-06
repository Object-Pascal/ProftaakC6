import java.time.LocalTime;

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

    public static double windDirection(short rawValue) {
        return rawValue;
    }

    public static double inchesHgToHectoPascal(short rawValue) {
        return (double)rawValue * 33.86388666666671 / 1000;
    }

    public static short humidity(short rawValue) {
        return rawValue;
    }

    public static double windSpeed(short rawValue) {
        return rawValue / 1.609344;
    }

    public static LocalTime sunRise(short rawValue) {
        String tijd = Integer.toString(rawValue);
        String output = "";
        if (tijd.length() == 4) {
            output = tijd.charAt(0) + tijd.charAt(1) +  ":" + tijd.charAt(2) + tijd.charAt(3);
        } else {
            output = "0" + tijd.charAt(0) +  ":" + tijd.charAt(1) + tijd.charAt(2);
        }
        return LocalTime.of(Integer.parseInt("" + output.charAt(0) + "" + output.charAt(1)), Integer.parseInt("" + output.charAt(3) + "" + output.charAt(4)));
    }

    public static LocalTime sunSet(short rawValue) {
        String output = "";
        String tijd = Integer.toString(rawValue);
        if (tijd.length() == 4) {
            output = "" + tijd.charAt(0) + tijd.charAt(1) +  ":" + tijd.charAt(2) + tijd.charAt(3);
        } else {
            output = "0" + tijd.charAt(0) +  ":" + tijd.charAt(1) + tijd.charAt(2);
        }
        return LocalTime.of(Integer.parseInt("" + output.charAt(0) + "" + output.charAt(1)), Integer.parseInt("" + output.charAt(3) + "" + output.charAt(4)));
    }

    public static double windChill(double temperature, double windSpeed) {
        double tempCelcius = temperature;
        double V = windSpeed;
        double windchill = 13.12 + ((0.6215 * tempCelcius) - (13.96 * Math.pow(V, 0.16))) + (0.4867 * (tempCelcius * Math.pow(V,0.16)));
        windchill = Math.round(windchill * 10.0)/10.0;
        return windchill;
    }

    /**
     * Found on: https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
     * @param value the number to be rounded
     * @param precision the precision
     * @return rounded number
     */
    public static double roundNumber (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
