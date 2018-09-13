public class ValueConverterTests {
    public static void main(String[] args) {
        testTemperature();
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
}
