public class ValueConverterTests {
    public static void main(String[] args) {
        testTemperature();
        testWindDirection();
        testUvIndex();
        testRainRate();
    }

    public static void testTemperature() {
        short testValueA = 810;
        double answerA = 27.2;
        double returnedData = ValueConverter.temperature(testValueA);

        System.out.println("Temperature inside = " + returnedData + " | Has to be: " + answerA);

        short testValueB = 673;
        double answerB = 19.6;
        returnedData = ValueConverter.temperature(testValueB);

        System.out.println("Temperature outside = " + returnedData + " | Has to be: " + answerB);
    }

    public static void testWindDirection() {
        short testValue = 277;
        String answer = "277°";
        String directionAnswer = ValueConverter.windDirection(testValue);

        System.out.println("Direction Calculated: " + directionAnswer + " | Should be: " + answer );
    }

    public static void testUvIndex() {
        short testValue = 38;
        double answer = 3.8;
        double calculated = ValueConverter.uvIndex(testValue);

        System.out.println("UVIndex calculated = " + calculated + " | Should be: " + answer);
    }

    public static void testRainRate() {
        short testValue = 185;
        double answer = 46.99;
        double calculated = ValueConverter.rainMeter(testValue);

        System.out.println("RainRate calculated = " + calculated + " | Should be: " + answer);
    }
}
