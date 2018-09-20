import java.time.LocalDateTime;
import java.util.Date;

public class Measurement {
    private double insideTemperature, outsideTemperature;
    private double airpressure;
    private short insideHumidity, outsideHumidity;
    private double windSpeed;
    private Date sunrise, sunset;

    public Measurement() {
        //RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
        //System.out.println(rawMeasurement);
    }

    public Measurement(RawMeasurement data) {
        ParseRawMeasurement(data);
    }

    private void ParseRawMeasurement(RawMeasurement data) {
        this.insideTemperature = ValueConverter.roundNumber(
                ValueConverter.temperature(data.getInsideTemp()) , 1);

        this.outsideTemperature = ValueConverter.roundNumber(
                ValueConverter.temperature(data.getOutsideTemp()), 1);

        this.airpressure = ValueConverter.inchesHgToHectoPascal(data.getBarometer());
        this.insideHumidity = ValueConverter.humidity(data.getInsideHum());
        this.outsideHumidity = ValueConverter.humidity(data.getOutsideTemp());
        this.windSpeed = ValueConverter.roundNumber(ValueConverter.windSpeed(data.getWindSpeed()), 1);
        this.sunrise = new Date();
    }

    private String timeStamp;

    public String getTimeStamp() { return timeStamp; }
    public void setTimeStamp(String value) { this.timeStamp = value; }
}
