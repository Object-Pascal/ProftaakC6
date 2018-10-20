import java.util.Arrays;

public class BdfCharacter {
    private int[][] bitmapData;
    private short encodingIndex;

    public BdfCharacter(int[][] bitmapData, short encodingIndex) {
        this.bitmapData = bitmapData;
        this.encodingIndex = encodingIndex;
    }

    @Override
    public String toString() {
        String[] arrays = new String[bitmapData.length];
        for (int i = 0; i < bitmapData.length; i++) {
            arrays[i] = Arrays.toString(bitmapData[i]);
        }
        
        return "BdfCharacter{" +
                "bitmapData=" + Arrays.toString(arrays) +
                ", index='" + encodingIndex + '\'' +
                '}';
    }

    public int[][] getBitmapData() {
        return bitmapData;
    }

    public short getEncodingIndex() {
        return encodingIndex;
    }
}
