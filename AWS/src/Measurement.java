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

    private String stationId;
    private LocalDateTime dateStamp;
    private short barometer;
    private short insideTemp;
    private short insideHum;
    private short outsideTemp;
    private short rawWindSpeed;
    private short avgWindSpeed;
    private short windDir;
    private short outsideHum;
    private short rainRate;
    private short UVLevel;
    private short solarRad;
    private short xmitBatt;
    private short battLevel;
    private short foreIcon;
    private short rawSunrise;
    private short rawSunset;

    public String getStationId() {return stationId;}
    public void setStationId(String value) {this.stationId = value;}

    public LocalDateTime getdateStamp() {return dateStamp;}
    public void setdateStamp(LocalDateTime value) {this.dateStamp = value;}

    public short getBarometer() {return barometer;}
    public void setBarometer(short value) {this.barometer = value;}

    public short getInsideTemp() {return insideTemp;}
    public void setInsideTemp(short value) {this.insideTemp = value;}

    public short getInsideHum() {return insideHum;}
    public void setInsideHum(short value) {this.insideHum = value;}

    public short getOutsideTemp() {return outsideTemp;}
    public void setOutsideTemp(short value) {this.outsideTemp = value;}

    public short getWindSpeed() {return rawWindSpeed;}
    public void setWindSpeed(short value) { this.rawWindSpeed = value;}

    public short getAvgWindSpeed() {return avgWindSpeed;}
    public void setAvgWindSpeed(short value) {this.avgWindSpeed = value;}

    public short getWindDir() {return windDir; }
    public void setWindDir(short value) {this.windDir = value;}

    public short getOutsideHum() {return outsideHum;}
    public void setOutsideHum(short value) {this.outsideHum = value;}

    public short getRainRate() {return rainRate;}
    public void setRainRate(short value) {this.rainRate = value;}

    public short getUVLevel() {return UVLevel;}
    public void setUVLevel(short value) {this.UVLevel = value;}

    public short getSolarRad() {return solarRad;}
    public void setSolarRad(short value) {this.solarRad = value;}

    public short getXmitBatt() {return xmitBatt;}
    public void setXmitBatt(short value) {this.xmitBatt = value;}

    public short getBattLevel() {return battLevel;}
    public void setBattLevel(short value) {this.battLevel = value;}

    public short getForeIcon() {return foreIcon;}
    public void setForeIcon(short value) {this.foreIcon = value;}

    public short getSunrise() {return rawSunrise;}
    public void setSunrise(short value) { this.rawSunrise = value;}

    public short getSunset() {return rawSunset;}
    public void setSunset(short value) { this.rawSunset = value;}



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
        this.sunrise = new Date();
    }

    @Override
    public String toString() {
        String s = "RawMeasurement:"
                + "\nstationId = \t" + stationId
                + "\ndateStamp = \t" + dateStamp
                + "\nbarometer = \t" + barometer
                + "\ninsideTemp = \t" + insideTemp
                + "\ninsideHum = \t" + insideHum
                + "\noutsideTemp = \t" + outsideTemp
                + "\nwindSpeed = \t" + windSpeed
                + "\navgWindSpeed = \t" + avgWindSpeed
                + "\nwindDir = \t" + windDir
                + "\noutsideHum = \t" + outsideHum
                + "\nrainRate = \t" + rainRate
                + "\nUVLevel = \t" + UVLevel
                + "\nsolarRad = \t" + solarRad
                + "\nxmitBatt = \t" + xmitBatt
                + "\nbattLevel = \t" + battLevel
                + "\nforeIcon = \t" + foreIcon
                + "\nsunrise = \th  , " + sunrise
                + "\nsunset = \t" + sunset;
        return s;
    }
}
