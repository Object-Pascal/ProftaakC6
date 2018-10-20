public class SegmentDisplay {
    private int displayAddress;

    public SegmentDisplay(int displayAddress) {
        this.displayAddress = displayAddress;
    }

    public void writeInt(int value) {
        IO.writeShort(displayAddress, value);
    }
}
