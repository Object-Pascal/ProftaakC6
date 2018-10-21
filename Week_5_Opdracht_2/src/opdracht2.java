public class opdracht2 {

    public static void main(String[] args) {
        IO.init();
        clear();
        int counterstart = 0;

        while (true) {
            if (IO.readShort(0x80) != 0 && (IO.readShort(0x90) == 0)) {
                counterstart = startCounter(counterstart);
            }
            if (IO.readShort(0x90) != 0 && (IO.readShort(0x80) == 0)) {
                startnegativeCounter(counterstart);
            }
        }
    }


    public static int startCounter(int counterstart) {
        for (int counter = counterstart ;counter < 100000; counter++) {
            if(IO.readShort(0x80) == 0){
                clear();
                return counter;
            }
            int number = counter;
            IO.writeShort(0x10, number % 10);
            number = number / 10;
            IO.writeShort(0x12, number % 10);
            number = number / 10;
            IO.writeShort(0x14, number % 10);
            number = number / 10;
            IO.writeShort(0x16, number% 10);
            number = number/ 10;
            IO.writeShort(0x18, number% 10);

            IO.delay(100);

        }
        return counterstart;
    }

    public static int startnegativeCounter(int counterstart) {
        for (int counter = counterstart ;counter > 0; counter--) {
            if(IO.readShort(0x90) == 0){
                clear();
                return counter;
            }
            int number = counter;
            IO.writeShort(0x10, number % 10);
            number = number / 10;
            IO.writeShort(0x12, number % 10);
            number = number / 10;
            IO.writeShort(0x14, number % 10);
            number = number / 10;
            IO.writeShort(0x16, number% 10);
            number = number/ 10;
            IO.writeShort(0x18, number% 10);

            IO.delay(100);

        }
        return counterstart;
    }

    public static void clear(){
        for(int i = 0x10; i < 0x35; i+= 0x02){
            IO.writeShort(i,0x100 | 1 << 8);
        }
    }
    }


