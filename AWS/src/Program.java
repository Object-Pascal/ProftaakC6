import java.util.ArrayList;

public class Program {
    public static PageManager pageManager;

    //Main entry point of the program
    public static void main(String[] args) {

        DisplayManager manager = new DisplayManager("127.0.0.1");
        IO.init();
        while(IO.readShort(0x80) != 0) {
            if(IO.readShort(0x100) == 1){
                //Hier komt de scroll methode!
                manager.writeText("test");
                IO.delay(500);
            }
            if(IO.readShort(0x90)==1){
                //hier komt de tabblad methode!
                manager.writeText("test2");
                IO.delay(500);
            }
            //ALLES WAT INVLOED HEEFT OP HET GUI MOET HIER IN!!!
        }
        manager.clearScreen();

        DisplayManager manager = DisplayManager.Initialize("127.0.0.1");
        /*
        ArrayList<SegmentDisplay> displays = manager.getDisplays();

        manager.setPixel(5, 5);
        manager.setPixel(5, 6);
        manager.setPixel(6, 5);
        manager.setPixel(6, 6);

        for (int i = 0; i < 128; i++) {
            manager.setPixel(0, 1, DISPLAYMATRIX_OPCODES.MOVE_DISPLAY);
            IO.delay(100);
        }*/
/*        ArrayList<IPageBehaviour> pages = new ArrayList<>();
        pages.add(() -> {
            DisplayManager.getInstance().writeText("Page 1");
            IO.delay(10);
            DisplayManager.getInstance().clearScreen();
        });

        pages.add(() -> {
            DisplayManager.getInstance().writeText("Page 2");
            IO.delay(10);
            DisplayManager.getInstance().clearScreen();
        });

        pages.add(() -> {
            DisplayManager.getInstance().writeText("Page 3");
            IO.delay(10);
            DisplayManager.getInstance().clearScreen();
        });

        pages.add(new EasterEggPage());

        pageManager = new PageManager(pages);

        boolean wasActive = false;
        while(true) {
            pageManager.showActivePage();

            if (IO.readShort(0x100) != 0 && !wasActive) {
                pageManager.nextPage();
                IO.writeShort(0x100, 0);
                wasActive = true;
            } else if (wasActive && IO.readShort(0x100) == 0)
                wasActive = false;
        }*/
    }
}