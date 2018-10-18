import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BdfParser {
    public static PixelFont bdfToCharacters(String filePath) {
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        ArrayList<String> currentData = new ArrayList<>();
        boolean newCharacterData = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("STARTCHAR")) {
                    newCharacterData = true;
                    currentData = new ArrayList<>();
                }


                if (newCharacterData)
                    currentData.add(line);


                if (line.contains("ENDCHAR"))
                    allData.add(currentData);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ArrayList<BdfCharacter> allCharacters = new ArrayList<>();
        for (ArrayList<String> dataSegment : allData) {
            allCharacters.add(BdfParser.dataToCharacter(dataSegment));
        }

        return new PixelFont(allCharacters.toArray(new BdfCharacter[allCharacters.size()]));
    }

    private static BdfCharacter dataToCharacter(ArrayList<String> data) {
        short encodingIndex = 0;
        int[][] bitmapData = new int[0][0];

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);
            if (line.contains("ENCODING"))
                encodingIndex = getEncoding(line);
            else if (line.contains("BBX"))
            {
                String[] sizeData = splitBBX(line);
                int width = Integer.parseInt(sizeData[0]);
                int height = Integer.parseInt(sizeData[1]);
                bitmapData = new int[height][width];
            }
            else if (line.contains("BITMAP")) {
                int heightItterator = 0;
                while(!data.get(++i).equals("ENDCHAR")) {
                    String binaryValue = hexToBinaryStr(data.get(i));
                    for (int x = 0; x < bitmapData[heightItterator].length; x++) {
                        bitmapData[heightItterator][x] = binaryValue.charAt(x) == '0' ? 0 : 1;
                    }
                    heightItterator++;
                }
            }
        }

        return new BdfCharacter(bitmapData, encodingIndex);
    }

    private static String hexToBinaryStr(String hexValue) {
        String binaryValue = Integer.toBinaryString(Integer.parseInt(hexValue, 16));
        if (binaryValue.length() < 8) {
            StringBuilder bldr = new StringBuilder();
            int amountMissing = 8 - binaryValue.length();
            for (int i = 0; i < amountMissing; i++) {
                bldr.append(0);
            }
            binaryValue = bldr.toString() + binaryValue;
        }
        return binaryValue;
    }

    private static short getEncoding(String line) {
        line = line.replace("ENCODING ", "");
        return Short.parseShort(line);
    }

    private static String[] splitBBX(String bbxData) {
        bbxData = bbxData.replace("BBX", "");
        String[] datas = bbxData.trim().split("\\s+");
        return datas;
    }
}
