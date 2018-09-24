import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;


public class ztest {

    public static void main(String[] args) {
        ArrayList<Double> temperature = new ArrayList();
        ArrayList<RawMeasurement> rawMeasurement = DatabaseConnection.getMeasurementsLastHour();
        for (int i = 0; i < rawMeasurement.size(); i++) {
            temperature.add(ValueConverter.temperature(rawMeasurement.get(i).getOutsideTemp()));
            System.out.println(temperature.get(i));
        }

    }

}
