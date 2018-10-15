
public class Buttons {

    public void clearGUI(){
        int opcode = 3;
        IO.writeShort(0x42, opcode << 12);
    }

}
