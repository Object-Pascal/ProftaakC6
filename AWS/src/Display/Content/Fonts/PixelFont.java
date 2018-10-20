import java.util.ArrayList;

public class PixelFont {
    private BdfCharacter[] characters;
    public int blockWidth = 3;
    public int blockHeight = 5;

    public PixelFont(BdfCharacter[] characters) {
        this.characters = characters;
    }

    public BdfCharacter findCharacter(char character) {
        BdfCharacter foundCharacter = null;
        for (BdfCharacter ch: characters) {
            if (ch.getEncodingIndex() == (short)character) {
                foundCharacter = ch;
                break;
            }
        }
        return foundCharacter;
    }
}
