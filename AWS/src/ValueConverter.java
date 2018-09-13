public class ValueConverter {

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

