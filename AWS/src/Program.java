import java.time.LocalTime;
import java.util.ArrayList;

public class Program {
    public static PageManager pageManager;
    public static Period periode;
    public static ArrayList<Measurement> measurements;
    //Main entry point of the program
    public static void main(String[] args) {
        periode = new Period(12);
        measurements = periode.getMeasurements();
        DisplayManager manager =  DisplayManager.Initialize("127.0.0.1");
        IO.init();
        pageManager =  new PageManager(loadPages());
        pageManager.printPageNumber();
        pageManager.showActivePage();
        while (IO.readShort(0x80) != 0) {
            klok();
            if (IO.readShort(0x100) == 1) {
                pageManager.nextPage();
                while(IO.readShort(0x100) == 1){

                }
                pageManager.printPageNumber();
            }
            if (IO.readShort(0x90) == 1) {
                pageManager.previousPage();
                while(IO.readShort(0x90) == 1)
                {
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
          DisplayManager.getInstance().writeText("Temperatuur (2/2)\n");
          DisplayManager.getInstance().writeText("Mod: " + (double)Math.round(periode.getModusInsideTemp()*10)/10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMed: " + (double)Math.round(periode.getMedianInsideTemp()*10)/10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingInsideTemp()*10)/10);
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
          DisplayManager.getInstance().writeText("Temp buiten (2/2)\n");
          DisplayManager.getInstance().writeText("Mod: " + (double)Math.round(periode.getModusOutsideTemp()*10)/10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMed: " + (double)Math.round(periode.getMedianOutsideTemp()*10)/10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingOutsideTemp()*10)/10);
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
          DisplayManager.getInstance().writeText("Vocht. binnen (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusInsideHumidity() * 10) / 10 + "% Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianInsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingInsideHumidity() * 10) / 10 + "%");
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
          DisplayManager.getInstance().writeText("Vocht. buiten (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusOutsideHumidity() * 10) / 10 + "% Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianOutsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingOutsideHumidity() * 10) / 10 + "%");
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
          DisplayManager.getInstance().writeText("Barometer (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusAirpressure()*10)/10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianAirpressure()*10)/10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingAirpressure()*10)/10);
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Regenval (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinRainrate() * 10) / 10 + "mm");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeRainrate() * 10) / 10 + "mm");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxRainrate() * 10) / 10 + "mm");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Regenval (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusRainrate() * 10) / 10 + "mm"+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianRainrate() * 10) / 10 + "mm");
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingRainrate() * 10) / 10 + "mm");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windsnelheid (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinWindSpeed() * 10) / 10);
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeWindSpeed() * 10) / 10);
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxWindspeed() * 10) / 10);
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windsnelheid (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusWindSpeed() * 10) / 10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianWindSpeed() * 10) / 10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingWindSpeed() * 10) / 10);
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windrichting: " + periode.windRichting());
          DisplayManager.getInstance().writeText("\nUVLevel: " + periode.getUVLevel());
          DisplayManager.getInstance().writeText("\nSolarRad: " + periode.getSolarRad());
          IO.delay(10);
      });

      pages.add(() -> {
          //TODO: SUNRISE SUNSET STIJN
          DisplayManager.getInstance().writeText("Sunrise: komt nog");
          DisplayManager.getInstance().writeText("\nSunset: komt nog");
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Graaddagen: " + periode.graaddagen());
          DisplayManager.getInstance().writeText("\nLangste droogte: " + periode.longestDrought().numberOfDays());
          DisplayManager.getInstance().writeText("\nTemp overlap: " + periode.tempOverlap());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Max regen.: " + (int)periode.maxAaneengeslotenRegenval());
          DisplayManager.getInstance().writeText(" dagen \nLangst zomer: " + periode.getLongestConnectedSummerDays().numberOfDays() + " dagen");
          IO.delay(10);
      });

      return pages;
  }
}