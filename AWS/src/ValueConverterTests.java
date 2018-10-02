import java.time.LocalTime;
import java.util.ArrayList;


public class ValueConverterTests {


    private int countermain;

    public static void main(String[] args) {
        /*
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

        System.out.println("------------------------------------------------------------------");
        Measurement measurement = new Measurement(DatabaseConnection.getMostRecentMeasurement());
        ArrayList<Measurement> lastvalues = new ArrayList<>();
        lastvalues.add(measurement);
        System.out.println("latest values: " + "\n" + lastvalues);
        */


        IO.init();
        clear();
        int counterstart = 0;
        while (true) {
            if (IO.readShort(0x80) != 0 && (IO.readShort(0x90) == 0)) {
                startCounter(counterstart);
            }
            if (IO.readShort(0x90) != 0 && (IO.readShort(0x80) == 0)) {
                startnegativeCounter(999);
            }
        }
    }


    public static int startCounter(int counterstart) {
        for (int counter = counterstart ;counter < 100000; counter++) {
            if(IO.readShort(0x80) == 0){
                return counter;
            }

            IO.writeShort(0x10, counter % 10);
            int counter2 = counter / 10;
            IO.writeShort(0x12, counter2 % 10);
            int counter3 = counter2 / 10;
            IO.writeShort(0x14, counter3% 10);
            int counter4 = counter3/ 10;
            IO.writeShort(0x16, counter4% 10);
            int counter5 = counter4/ 10;
            IO.writeShort(0x18, counter5% 10);

            IO.delay(100);

        }
        return counterstart;
    }

    public static int startnegativeCounter(int counterstart) {
        for (int counter = counterstart; counter > 0; counter--) {
            if(IO.readShort(0x90) == 0){
                return counter;
            }

            IO.writeShort(0x10, counter % 10);
            int number = counter / 10;
            IO.writeShort(0x12, number % 10);
            int number2 = number / 10;
            IO.writeShort(0x14, number2 % 10);
            int number3 = number2 / 10;
            IO.writeShort(0x16, number3 % 10);
            int number4 = number3 / 10;
            IO.writeShort(0x18, number4 % 10);

            IO.delay(100);
        }
        return counterstart;
    }

    public static void clear(){
       for(int i = 0x10; i < 0x35; i+= 0x02){
           IO.writeShort(i,0x100 | 1 << 8);
       }
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
        double answer = -25;
        double calculated = ValueConverter.windChill(testValue1, testValue2);

        System.out.println("Windchill calculated = " + calculated + " | Should be: " + answer);
    }
}
