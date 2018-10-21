import java.time.LocalTime;
import java.util.ArrayList;

public class Program {
    public static PageManager pageManager;
    public static Period periode;
    public static ArrayList<Measurement> measurements;
    //Main entry point of the program
    public static void main(String[] args) {
        periode = new Period(120);
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

    public static void actueel(double getal) {
        if (getal < 100) {
            int eentallen;
            int tientallen;
            int honderdtallen;
            double getalD = getal * 10;
            int rekenGetal = (int) getalD;
            if (rekenGetal > 99) {
                honderdtallen = rekenGetal / 100;
                IO.writeShort(0x34, honderdtallen);
                rekenGetal = rekenGetal - (honderdtallen * 100);

                tientallen = rekenGetal / 10;
                getalMetPunt(tientallen);
                rekenGetal = rekenGetal - (tientallen * 10);
                eentallen = rekenGetal;
                IO.writeShort(0x30, eentallen);

            } else if (rekenGetal > 9) {
                tientallen = rekenGetal / 10;
                getalMetPunt(tientallen);
                rekenGetal = rekenGetal - (tientallen * 10);
                eentallen = rekenGetal;
                IO.writeShort(0x30, eentallen);

            } else {
                eentallen = rekenGetal;
                IO.writeShort(0x30, eentallen);
            }
        }
    }
    public static void getalMetPunt(int tientallen)
    {
        if (tientallen == 1) {
            IO.writeShort(0x32, 0x186);
        } else if (tientallen == 2) {
            IO.writeShort(0x32, 0x1DB);
        } else if (tientallen == 3) {
            IO.writeShort(0x32, 0x1CF);
        } else if (tientallen == 4) {
            IO.writeShort(0x32, 0x1E6);
        } else if (tientallen == 5) {
            IO.writeShort(0x32, 0x1ED);
        } else if (tientallen == 6) {
            IO.writeShort(0x32, 0x1FD);
        } else if (tientallen == 7) {
            IO.writeShort(0x32, 0x187);
        } else if (tientallen == 8) {
            IO.writeShort(0x32, 0x1FF);
        } else if (tientallen == 9) {
            IO.writeShort(0x32, 0x1EF);
        } else if (tientallen == 0) {
            IO.writeShort(0x32, 0x1BF);
        } else {
        }
    }
public static void clearRechts(){
        IO.writeShort(0x30,0x100);
        IO.writeShort(0x32,0x100);
        IO.writeShort(0x34,0x100);
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
      Measurement measurement = new Measurement(DatabaseConnection.getMostRecentMeasurement());

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Temp binnen (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinInsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeInsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxInsideTemp() * 10)/10);
          actueel(measurement.getInsideTemperature());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Temp binnen (2/2)\n");
          DisplayManager.getInstance().writeText("Mod: " + (double)Math.round(periode.getModusInsideTemp()*10)/10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMed: " + (double)Math.round(periode.getMedianInsideTemp()*10)/10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingInsideTemp()*10)/10);
          actueel(measurement.getInsideTemperature());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Temp buiten (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinOutsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeOutsideTemp() * 10)/10);
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxOutsideTemp() * 10)/10);
          actueel(measurement.getOutsideTemperature());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Temp buiten (2/2)\n");
          DisplayManager.getInstance().writeText("Mod: " + (double)Math.round(periode.getModusOutsideTemp()*10)/10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMed: " + (double)Math.round(periode.getMedianOutsideTemp()*10)/10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingOutsideTemp()*10)/10);
          actueel(measurement.getOutsideTemperature());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Vocht. binnen (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinInsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeInsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxInsideHumidity() * 10) / 10 + "%");
          actueel(measurement.getInsideHumidity());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Vocht. binnen (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusInsideHumidity() * 10) / 10 + "% Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianInsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingInsideHumidity() * 10) / 10 + "%");
          actueel(measurement.getInsideHumidity());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Vocht. buiten (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinOutsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeOutsideTemp() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxOutsideTemp() * 10) / 10 + "%");
          actueel(measurement.getOutsideHumidity());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Vocht. buiten (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusOutsideHumidity() * 10) / 10 + "% Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianOutsideHumidity() * 10) / 10 + "%");
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingOutsideHumidity() * 10) / 10 + "%");
          actueel(measurement.getOutsideHumidity());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Barometer (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + Math.round(periode.getMinAirpressure()));
          DisplayManager.getInstance().writeText(" GEM: " + Math.round(periode.getGemiddeldeAirpressure()));
          DisplayManager.getInstance().writeText("\nMAX: " + Math.round(periode.getMaxAirpressure()));
          clearRechts();
          IO.delay(10);
      });
      pages.add(() -> {
          DisplayManager.getInstance().writeText("Barometer (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusAirpressure()*10)/10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianAirpressure()*10)/10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingAirpressure()*10)/10);
          clearRechts();
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Regenval (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinRainrate() * 10) / 10 + "mm");
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeRainrate() * 10) / 10 + "mm");
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxRainrate() * 10) / 10 + "mm");
          actueel(measurement.getRainRate());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Regenval (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusRainrate() * 10) / 10 + "mm"+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianRainrate() * 10) / 10 + "mm");
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingRainrate() * 10) / 10 + "mm");
          actueel(measurement.getRainRate());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windsnelheid (1/2)\n");
          DisplayManager.getInstance().writeText("MIN: " + (double)Math.round(periode.getMinWindSpeed() * 10) / 10);
          DisplayManager.getInstance().writeText(" GEM: " + (double)Math.round(periode.getGemiddeldeWindSpeed() * 10) / 10);
          DisplayManager.getInstance().writeText("\nMAX: " + (double)Math.round(periode.getMaxWindspeed() * 10) / 10);
          actueel(measurement.getWindSpeed());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windsnelheid (2/2)\n");
          DisplayManager.getInstance().writeText("MOD: " + (double)Math.round(periode.getModusWindSpeed() * 10) / 10+" Stdafw:");
          DisplayManager.getInstance().writeText("\nMED: " + (double)Math.round(periode.getMedianWindSpeed() * 10) / 10);
          DisplayManager.getInstance().writeText("    " + (double)Math.round(periode.getStandaardafwijkingWindSpeed() * 10) / 10);
          actueel(measurement.getWindSpeed());
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Windrichting: " + periode.windRichting());
          DisplayManager.getInstance().writeText("\nUVLevel: " + periode.getUVLevel());
          DisplayManager.getInstance().writeText("\nSolarRad: " + periode.getSolarRad());
            clearRechts();
          IO.delay(10);
      });

      pages.add(() -> {
          LocalTime sunrise = measurement.getSunrise();
          LocalTime sunset = measurement.getSunset();
          DisplayManager.getInstance().writeText(String.format("  Sunrise: %s\n", sunrise.toString()));//sunrise.getHour() < 10 ? "0" + sunrise.getHour() : sunrise.getHour(), sunrise.getMinute() < 10 ? "0" + sunrise.getMinute() : sunrise.getMinute()));
          DisplayManager.getInstance().writeText(String.format("  Sunset : %s", sunset.toString()));
          clearRechts();
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Graaddagen: " + periode.graaddagen());
          DisplayManager.getInstance().writeText("\nLangste droogte: " + periode.longestDrought().numberOfDays());
          DisplayManager.getInstance().writeText("\nTemp overlap: " + periode.tempOverlap());
clearRechts();
          IO.delay(10);
      });

      pages.add(() -> {
          DisplayManager.getInstance().writeText("Max regen: " + (int)periode.maxAaneengeslotenRegenval());
          DisplayManager.getInstance().writeText(" mm \nLangst zomer: " + periode.getLongestConnectedSummerDays().numberOfDays() + " dagen");
          DisplayManager.getInstance().writeText(" mm \nDagen mist: " + periode.mistdagen() + " dagen");
          clearRechts();
          IO.delay(10);
      });

      return pages;
  }
}