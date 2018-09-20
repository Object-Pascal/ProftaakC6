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
        this.insideTemperature = ValueConverter.roundNumber(
                ValueConverter.temperature(data.getInsideTemp()), 1);

        this.outsideTemperature = ValueConverter.roundNumber(
                ValueConverter.temperature(data.getOutsideTemp()), 1);

        this.airpressure = ValueConverter.inchesHgToHectoPascal(data.getBarometer());
        this.insideHumidity = ValueConverter.humidity(data.getInsideHum());
        this.outsideHumidity = ValueConverter.humidity(data.getOutsideTemp());

        this.windSpeed = ValueConverter.roundNumber(ValueConverter.windSpeed(data.getWindSpeed()), 1);
        this.averageWindSpeed = ValueConverter.roundNumber(ValueConverter.windSpeed(data.getAvgWindSpeed()), 1);

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
                + "\nsunrise = \th  , " + sunrise
                + "\nsunset = \t" + sunset;
        return s;
    }

    public String getStationId() {return stationId;}
    public void setStationId(String value) {this.stationId = value;}

    public LocalDateTime getdateStamp() {return dateStamp;}
    public void setdateStamp(LocalDateTime value) {this.dateStamp = value;}

    public double getBarometer() {return airpressure;}
    public void setBarometer(double value) {this.airpressure = value;}

    public double getInsideTemp() {return insideTemperature;}
    public void setInsideTemp(double value) {this.insideTemperature = value;}

    public short getInsideHum() {return insideHumidity;}
    public void setInsideHum(short value) {this.insideHumidity = value;}

    public double getOutsideTemp() {return outsideTemperature;}
    public void setOutsideTemp(double value) {this.outsideTemperature = value;}

    public double getWindSpeed() {return windSpeed;}
    public void setWindSpeed(double value) { this.windSpeed = value;}

    public double getAvgWindSpeed() {return averageWindSpeed;}
    public void setAvgWindSpeed(double value) {this.averageWindSpeed = value;}

    public double getWindChill() {return windChill;}
    public void setWindChill(double value) {this.windChill = value;}

    public String getWindDir() {return windDirection; }
    public void setWindDir(String value) {this.windDirection = value;}

    public short getOutsideHum() {return outsideHumidity;}
    public void setOutsideHum(short value) {this.outsideHumidity = value;}

    public double getRainRate() {return rainRate;}
    public void setRainRate(double value) {this.rainRate = value;}

    public double getUVLevel() {return uvIndex;}
    public void setUVLevel(double value) {this.uvIndex = value;}

    public double getSolarRad() {return solarRadiaton;}
    public void setSolarRad(double value) {this.solarRadiaton = value;}

    public short getXmitBatt() {return xmitBatt;}
    public void setXmitBatt(short value) {this.xmitBatt = value;}

    public short getBattLevel() {return battLevel;}
    public void setBattLevel(short value) {this.battLevel = value;}

    public short getForeIcon() {return foreIcon;}
    public void setForeIcon(short value) {this.foreIcon = value;}

    public LocalTime getSunrise() {return sunrise;}
    public void setSunrise(LocalTime value) { this.sunrise = value;}

    public LocalTime getSunset() {return sunset;}
    public void setSunset(LocalTime value) { this.sunset = value;}
}
