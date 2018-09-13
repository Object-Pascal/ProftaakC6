public class ValueConverterTests {
    public static void main(String[] args) {
        testTemperature();
        testInchesHgToHectoPascal();
        testHumidity();
        testWindSpeed();
        testSunRise();
        testSunSet();
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
        String returnedData = ValueConverter.humidity(testValueA);

        System.out.println("The humidity = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testWindSpeed() {
        short testValueA = 14;
        double answerA = 3.7;
        double returnedData = ValueConverter.windSpeed(testValueA);

        System.out.println("Windspeed = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testSunRise() {
        short testValueA = 714;
        String answerA = "07:14";
        String returnedData = ValueConverter.sunRise(testValueA);

        System.out.println("Time of sunrise = " + returnedData + " | Has to be: " + answerA);
    }

    public static void testSunSet() {
        short testValueA = 2001;
        String answerA = "20:01";
        String returnedData = ValueConverter.sunSet(testValueA);

        System.out.println("Time of sunset = " + returnedData + " | Has to be: " + answerA);
    }



}
