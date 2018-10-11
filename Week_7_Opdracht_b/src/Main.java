import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        System.out.println(TemperatureAddons.fetchTemperatureExceedings(new Period(
                    LocalDate.parse("2018-09-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDate.parse("2018-10-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                EXCEEDING_TYPE.OUTSIDE_EXCEEDS_INSIDE)
                + " outside > inside exceedings.");

        System.out.println(TemperatureAddons.fetchTemperatureExceedings(new Period(
                    LocalDate.parse("2018-09-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDate.parse("2018-10-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                EXCEEDING_TYPE.INSIDE_EXCEEDS_OUTSIDE)
                + " inside > outside exceedings.");
    }
}
