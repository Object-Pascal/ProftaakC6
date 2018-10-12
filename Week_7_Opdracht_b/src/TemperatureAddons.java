import java.util.ArrayList;

public class TemperatureAddons {
    public static int fetchTemperatureExceedings(Period period, EXCEEDING_TYPE type) {
        int exceedings = 0;
        ArrayList<RawMeasurement> rawmeasurements = period.getRawMeasurements();

        for (int i = 0; i < rawmeasurements.size(); i++) {
            if (type == EXCEEDING_TYPE.INSIDE_EXCEEDS_OUTSIDE) {
                if (rawmeasurements.get(i).getInsideTemp() > rawmeasurements.get(i).getOutsideTemp()) {
                    exceedings++;
                }
            }
            else if(type == EXCEEDING_TYPE.OUTSIDE_EXCEEDS_INSIDE) {
                if (rawmeasurements.get(i).getOutsideTemp() > rawmeasurements.get(i).getInsideTemp()) {
                    exceedings++;
                }
            }
        }
        return exceedings;
    }
}