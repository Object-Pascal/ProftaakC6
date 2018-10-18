import java.time.LocalTime;
import java.util.ArrayList;

public class Program {
    public static PageManager pageManager;
    public static Period periode;
    public static ArrayList<Measurement> measurements;
    //Main entry point of the program
    public static void main(String[] args) {
        periode = new Period();
        measurements = periode.getMeasurements();
        DisplayManager manager =  DisplayManager.Initialize("127.0.0.1");
        IO.init();
        pageManager =  new PageManager(loadPages());
        pageManager.printPageNumber();
        pageManager.showActivePage();
        //main loop
        while (IO.readShort(0x80) != 0) {
            klok();
            //checkt voor input voor nextpage
            if (IO.readShort(0x100) == 1) {
                pageManager.nextPage();
                while(IO.readShort(0x100) == 1){
                    //speciale skip functie (skipt 5 tabs)
                    if(IO.readShort(0x90)==1){
                        for (int i = 0; i < 4 ; i++) {
                            IO.delay(200);
                            pageManager.nextPage();
                        }
                        break;
                    }
                }
                pageManager.printPageNumber();
            }
            if (IO.readShort(0x90) == 1) {
                //checkt voor input voor previouspage
                pageManager.previousPage();
                while(IO.readShort(0x90) == 1) {
                    //speciale skip functie
                    if(IO.readShort(0x100)==1){
                        for (int i = 0; i < 4; i++) {
                            IO.delay(200);
                            pageManager.previousPage();
                        }
                        break;
                    }
                }
                pageManager.printPageNumber();
            }
            pageManager.showActivePage();
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



          DisplayManager.getInstance().writeText("Temperatuur (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinInsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeInsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxInsideTemp() * 10)/10);

          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 2");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Temp buiten (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinOutsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeOutsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxOutsideTemp() * 10)/10);
          IO.delay(10);
      });
      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 4");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Vocht. binnen (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinInsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeInsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxInsideHumidity() * 10) / 10 + "%");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 6");
          IO.delay(10);
      });
      pages.add(() -> {
          DisplayManager.getInstance().writeText("Vocht. buiten (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinOutsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeOutsideTemp() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxOutsideTemp() * 10) / 10 + "%");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 8");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Barometer (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + Math.round(periode.getMinAirpressure()));
          DisplayManager.getInstance().writeText(" GEM: " + Math.round(periode.getGemiddeldeAirpressure()));
          DisplayManager.getInstance().writeText("\nMAX: " + Math.round(periode.getMaxAirpressure()));
          IO.delay(10);
      });
      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 10");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Regenval (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinRainrate() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeRainrate() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxRainrate() * 10) / 10 + "%");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 12");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windsnelheid (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + Math.round(periode.getMinWindSpeed()));
          DisplayManager.getInstance().writeText(" GEM: " + Math.round(periode.getGemiddeldeWindSpeed()));
          DisplayManager.getInstance().writeText("\nMAX: " + Math.round(periode.getMaxWindspeed()));
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 14");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 15");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 16");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 17");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 18");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 19");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 20");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 21");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Page 22");
          IO.delay(10);
      });
      return pages;
  }
}