import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Period period = new Period(LocalDate.parse("2018-07-10"), LocalDate.parse("2018-10-10"));
        Period longestDrought = period.longestDraught();
    }
}
