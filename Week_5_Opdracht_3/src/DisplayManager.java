import java.util.ArrayList;

public class DisplayManager {
    private String ip;
    private int[] addresses = {0x18,0x16,0x14,0x12,0x10,0x24,0x22,0x20,0x34,0x32,0x30};

    private DisplayManager(String ip) {
        this.ip = ip;
        IO.init(ip);
    }
    
    public void writeText(String text) {
        for (int i = 0; i < text.length(); i++) {
            IO.writeShort(0x40, text.charAt(i));
        }
    }

    public void setPixel(int x, int y) {
        this.setPixel(x, y, DISPLAYMATRIX_OPCODES.DRAW_PIXEL);
    }

    public void setPixel(int x, int y, int opcode) {
        IO.writeShort(0x42, (opcode << 12 | x << 5 | y));
    }

    public void clearScreen() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }

    public ArrayList<SegmentDisplay> getDisplays() {
        ArrayList<SegmentDisplay> displays = new ArrayList<>();
        for (int i = 0; i < addresses.length; i++) {
            displays.add(new SegmentDisplay(addresses[i]));
        }
        return displays;
    }

    private static DisplayManager instance;
    public static DisplayManager Initialize(String ip) {
        if (instance == null)
            instance = new DisplayManager(ip);

        return instance;
    }

    public static DisplayManager getInstance() { return instance; }
    public static boolean isInitialized() {return instance != null;}
}
