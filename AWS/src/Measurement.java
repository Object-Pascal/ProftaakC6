public class Measurement {
    public Measurement() {
        //RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
        //System.out.println(rawMeasurement);
    }

    private String timeStamp;

    public String getTimeStamp() { return timeStamp; }
    public void setTimeStamp(String value) { this.timeStamp = value; }

    @Override
    public String toString()
    {
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
                + "\nsunrise = \t" + sunrise
                + "\nsunset = \t" + sunset;
        return s;
    }

}
