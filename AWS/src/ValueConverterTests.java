import java.time.LocalTime;

public class ValueConverterTests {
    public static void main(String[] args) {
        testTemperature();
        testInchesHgToHectoPascal();
        testHumidity();
        testWindSpeed();
        testSunRise();
        testSunSet();
        testWindDirection();
        testUvIndex();
        testRainRate();
        testWindChill();
<<<<<<< HEAD
        Measurement measurement = new Measurement(DatabaseConnection.getMostRecentMeasurement());
        System.out.println(measurement.getStationId());
        System.out.println(measurement.getdateStamp());
        System.out.println(measurement.getBarometer());
        System.out.println(measurement.getInsideTemp());
        System.out.println(measurement.getInsideHum());
        System.out.println(measurement.getOutsideTemp());
        System.out.println(measurement.getWindSpeed());
        System.out.println(measurement.getWindChill());
        System.out.println(measurement.getWindDir());
        System.out.println(measurement.getOutsideHum());
        System.out.println(measurement.getRainRate());
        System.out.println(measurement.getUVLevel());
        System.out.println(measurement.getSolarRad());
        System.out.println(measurement.getXmitBatt());
        System.out.println(measurement.getBattLevel());
        System.out.println(measurement.getForeIcon());
        System.out.println(measurement.getSunrise());
        System.out.println(measurement.getSunset());

=======

        RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
        Measurement measurement = new Measurement(rawMeasurement);

        System.out.println(measurement);
>>>>>>> 4a0e6e921fa6b4c6ca411ce0a3f121cf1727a573
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

    public static void testInchesHgToHectoPascal() {
        short testValueA = 30233;
        double answerA = 1023;
        double returnedData = ValueConverter.inchesHgToHectoPascal(testValueA);

        System.out.println("Air pressure = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testHumidity() {
        short testValueA = 52;
        String answerA = "52%";
        short returnedData = ValueConverter.humidity(testValueA);

        System.out.println("The humidity = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testWindSpeed() {
        short testValueA = 2;
        double answerA = 1.2;
        double returnedData = ValueConverter.windSpeed(testValueA);

        System.out.println("Windspeed = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testSunRise() {
        short testValueA = 714;
        String answerA = "07:14";
        LocalTime returnedData = ValueConverter.sunRise(testValueA);

        System.out.println("Time of sunrise = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testSunSet() {
        short testValueA = 2001;
        String answerA = "20:01";
        LocalTime returnedData = ValueConverter.sunSet(testValueA);

        System.out.println("Time of sunset = " + returnedData + " | Has to be: " + answerA);
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

    public static void testWindChill() {
        short windSpeedRaw = 3;
        short temperatureRaw = 50;
        double testValue2 = ValueConverter.windSpeed(windSpeedRaw);
        double testValue1 = ValueConverter.temperature(temperatureRaw);
        double answer = -19.7;
        double calculated = ValueConverter.windChill(testValue1, testValue2);

        System.out.println("Windchill calculated = " + calculated + " | Should be: " + answer);
    }
}
