import sun.awt.SunHints;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Measurement {
    private String stationId;
    private LocalDateTime dateStamp;

    private double insideTemperature, outsideTemperature;
    private double airpressure;
    private short insideHumidity, outsideHumidity;
    private double windSpeed, averageWindSpeed;
    private LocalTime sunrise, sunset;
    private String windDirection;
    private double windChill;
    private double rainRate;
    private double uvIndex;
    private double solarRadiaton;
    private short xmitBatt, battLevel;
    private short foreIcon;

    public Measurement() {
        //RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
        //System.out.println(rawMeasurement);
    }

    public Measurement(RawMeasurement data) {
        ParseRawMeasurement(data);
    }

    private void ParseRawMeasurement(RawMeasurement data) {
        this.insideTemperature = ValueConverter.temperature(data.getInsideTemp());
        this.outsideTemperature = ValueConverter.temperature(data.getOutsideTemp());

        this.airpressure = ValueConverter.inchesHgToHectoPascal(data.getBarometer());
        this.insideHumidity = ValueConverter.humidity(data.getInsideHum());
        this.outsideHumidity = ValueConverter.humidity(data.getOutsideTemp());

        this.windSpeed = ValueConverter.windSpeed(data.getWindSpeed());
        this.averageWindSpeed = ValueConverter.windSpeed(data.getAvgWindSpeed());

        this.sunrise = ValueConverter.sunRise(data.getSunrise());
        this.sunset = ValueConverter.sunSet(data.getSunset());

        this.windChill = ValueConverter.windChill(windSpeed, outsideTemperature);
        this.windDirection = ValueConverter.windDirection(data.getWindDir());

        this.rainRate = ValueConverter.rainMeter(data.getRainRate());

        this.uvIndex = ValueConverter.uvIndex(data.getUVLevel());
        this.solarRadiaton = data.getSolarRad();

        this.xmitBatt = data.getXmitBatt();
        this.battLevel = data.getBattLevel();
        this.foreIcon = data.getForeIcon();
    }

    @Override
    public String toString() {
        String s = "RawMeasurement:"
                + "\nstationId = \t" + stationId
                + "\ndateStamp = \t" + dateStamp
                + "\nbarometer = \t" + airpressure
                + "\ninsideTemp = \t" + insideTemperature
                + "\ninsideHum = \t" + insideHumidity
                + "\noutsideTemp = \t" + outsideTemperature
                + "\nwindSpeed = \t" + windSpeed
                + "\navgWindSpeed = \t" + averageWindSpeed
                + "\nwindDir = \t" + windDirection
                + "\noutsideHum = \t" + outsideHumidity
                + "\nrainRate = \t" + rainRate
                + "\nUVLevel = \t" + uvIndex
                + "\nsolarRad = \t" + solarRadiaton
                + "\nxmitBatt = \t" + xmitBatt
                + "\nbattLevel = \t" + battLevel
                + "\nforeIcon = \t" + foreIcon
                + "\nsunrise = \t" + sunrise
                + "\nsunset = \t" + sunset;
        return s;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(LocalDateTime dateStamp) {
        this.dateStamp = dateStamp;
    }

    public double getInsideTemperature() {
        return insideTemperature;
    }

    public void setInsideTemperature(double insideTemperature) {
        this.insideTemperature = insideTemperature;
    }

    public double getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public double getAirpressure() {
        return airpressure;
    }

    public void setAirpressure(double airpressure) {
        this.airpressure = airpressure;
    }

    public short getInsideHumidity() {
        return insideHumidity;
    }

    public void setInsideHumidity(short insideHumidity) {
        this.insideHumidity = insideHumidity;
    }

    public short getOutsideHumidity() {
        return outsideHumidity;
    }

    public void setOutsideHumidity(short outsideHumidity) {
        this.outsideHumidity = outsideHumidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getAverageWindSpeed() {
        return averageWindSpeed;
    }

    public void setAverageWindSpeed(double averageWindSpeed) {
        this.averageWindSpeed = averageWindSpeed;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindChill() {
        return windChill;
    }

    public void setWindChill(double windChill) {
        this.windChill = windChill;
    }

    public double getRainRate() {
        return rainRate;
    }

    public void setRainRate(double rainRate) {
        this.rainRate = rainRate;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public double getSolarRadiaton() {
        return solarRadiaton;
    }

    public void setSolarRadiaton(double solarRadiaton) {
        this.solarRadiaton = solarRadiaton;
    }

    public short getXmitBatt() {
        return xmitBatt;
    }

    public void setXmitBatt(short xmitBatt) {
        this.xmitBatt = xmitBatt;
    }

    public short getBattLevel() {
        return battLevel;
    }

    public void setBattLevel(short battLevel) {
        this.battLevel = battLevel;
    }

    public short getForeIcon() {
        return foreIcon;
    }

    public void setForeIcon(short foreIcon) {
        this.foreIcon = foreIcon;
    }
}
