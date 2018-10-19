public class Page implements IPageBehaviour {
    public Page() {

    }

    public void showPage() {

    }
}

class EasterEggPage extends Page {
    private int length = 0;
    private int minLength = 0;
    private int maxLength = 128;
    private int maxHeight = 32;

    public EasterEggPage() {

    }

    @Override
    public void showPage() {
        DisplayManager.getInstance().clearScreen();
        for (int i = 0; i < maxHeight; i++) {
            DisplayManager.getInstance().setPixel(length, i, DISPLAYMATRIX_OPCODES.DRAW_PIXEL);
        }
        IO.delay(5);

        if (length >= maxLength)
            length = minLength;
        else
            length++;
    }
}

interface IPageBehaviour {
    void showPage();
}