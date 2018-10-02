import java.util.ArrayList;

public class Main {
    private static DisplayManager display;

    public static void main(String[] args) {
        initializeDisplay();
        displayTemperature(getLatestMeasurement());
    }

    private static void initializeDisplay() {
        DisplayManager.Initialize("localhost");
        display = DisplayManager.getInstance();
        display.clearScreen();
    }

    private static void displayTemperature(Measurement measurement) {
        ArrayList<SegmentDisplay> segments =  display.getDisplays();

        String txtRepr = String.format("%.1f", measurement.getOutsideTemperature());
        for (int i = 0; i < txtRepr.length(); i++) {
            if (txtRepr.charAt(i) == '.') {
                segments.get(i).writeDot();
            }
            else {
                segments.get(i).writeInt(txtRepr.charAt(i));
            }
        }
    }

    private static Measurement getLatestMeasurement() {
        return new Measurement(DatabaseConnection.getMostRecentMeasurement());
    }
}
