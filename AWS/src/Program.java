import java.util.ArrayList;

public class Program {
    //Main entry point of the program
    public static void main(String[] args) {
        DisplayManager manager = DisplayManager.Initialize("145.49.13.121");

        ArrayList<SegmentDisplay> displays = manager.getDisplays();

        manager.setPixel(5, 5);
        manager.setPixel(5, 6);
        manager.setPixel(6, 5);
        manager.setPixel(6, 6);

        for (int i = 0; i < 128; i++) {
            manager.setPixel(0, 1, DISPLAYMATRIX_OPCODES.MOVE_DISPLAY);
            IO.delay(100);
        }
    }
}