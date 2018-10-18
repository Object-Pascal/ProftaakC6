import java.time.LocalTime;
import java.util.ArrayList;

public class Program {
    public static PageManager pageManager;

    //Main entry point of the program
    public static void main(String[] args) {
        DisplayManager manager =  DisplayManager.Initialize("127.0.0.1");
        IO.init();
        pageManager =  new PageManager(loadPages());
        while (IO.readShort(0x80) != 0) {
            klok();
            pageManager.showActivePage();
            if (IO.readShort(0x100) == 1) {
                pageManager.nextPage();
                while(IO.readShort(0x100) == 1){

                }
            }
            if (IO.readShort(0x90) == 1) {
                pageManager.previousPage();
                while(IO.readShort(0x90) == 1){

                }
            }
            IO.delay(50);
        }
        manager.clearAll();
    }


    private static void klok() {
        LocalTime localTime = LocalTime.now();
        String uur = "" + localTime.getHour();
        String minuut = "" + localTime.getMinute();
        if (uur.length() == 1) {
            uur = "0" + uur;
        }
        if (minuut.length() == 1) {
            minuut = "0" + minuut;
        }
        IO.writeShort(0x18, uur.charAt(0));
        IO.writeShort(0x16, uur.charAt(1));
        IO.writeShort(0x12, minuut.charAt(0));
        IO.writeShort(0x10, minuut.charAt(1));
    }


  public static ArrayList<IPageBehaviour> loadPages() {
      ArrayList<IPageBehaviour> pages = new ArrayList<>();
      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 1");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 2");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 3");
          IO.delay(10);
      });
      return pages;
  }
/*
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