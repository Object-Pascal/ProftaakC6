import java.util.ArrayList;

public class DisplayManager {
    private String ip;
    private int[] addresses = {0x18,0x16,0x14,0x12,0x10,0x24,0x22,0x20,0x34,0x32,0x30};
    private PixelFont pixelFont;

    public DisplayManager(String ip) {
        this.ip = ip;
        IO.init(ip);


        this.pixelFont = BdfParser.bdfToCharacters("resources\\tom-thumb.bdf");

    }
    
    public void writeText(String text) {
        for (int i = 0; i < text.length(); i++) {
            IO.writeShort(0x40, text.charAt(i));
        }
    }

    public void writeBdfText(int x, int y, String text) {
        for (int i = 0; i < text.length(); i++) {
            BdfCharacter character = pixelFont.findCharacter(text.charAt(i));
            drawBdfCharacter(x, y, character);
            x += pixelFont.blockWidth;
        }
    }

    private void drawBdfCharacter(int posx, int posy, BdfCharacter character) {
        int[][] drawData = character.getBitmapData();
        for (int y = 0; y < drawData.length; y++) {
            for (int x = 0; x < drawData[y].length; x++) {
                boolean drawPixel = drawData[y][x] != 0;
                if (drawPixel)
                    setPixel(posx + x, posy + y);
            }
        }
    }

    public void setPixel(int x, int y) {
        this.setPixel(x, y, DISPLAYMATRIX_OPCODES.DRAW_PIXEL);
    }

    public void setPixel(int x, int y, int opcode) {
        IO.writeShort(0x42, (opcode << 12 | x << 5 | y));
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

    public void clearScreen() {
        IO.writeShort(0x40, 0xFE);
        IO.writeShort(0x40, 0x01);
    }

    public void clearSegments() {
        getDisplays().forEach(x -> x.writeInt(0x100));
    }

    public void clearAll() {
        clearScreen();
        clearSegments();
    }



    public static DisplayManager getInstance() { return instance; }
    public static boolean isInitialized() {return instance != null;}
}
