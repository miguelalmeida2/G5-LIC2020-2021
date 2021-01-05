

public class TUI {



    public static void main(String[] args){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();
        init();

        clearScreen();
        write(" Roulette Game  ");
        setCursorLine(true);
        write(" 1 X 2 X 3 X" + M.coinDigitDim() + "$" + M.totalCoins);


        //wait for game start
        waitforKey('*');
        clearScreen();
        setCursorLine(true);
        write("0123456789  " + M.coinDigitDim() + "$" + M.totalCoins);
        while (true){
            RouletteGameApp.placeBet((int)(readBets()-'0'));
        }

    }

    public static void init(){

    }

    public static char readBets(){
        char key = 'a';
        while (key == 'a'){
            key = KBD.getKey();
            System.out.println((int)(key-'0'));
        }return key;
    }

    public static void waitforKey(char keyExpected){
        char key = 0;
        while (key != keyExpected){
            key = KBD.getKey();
        }
    }

    public static void write(String text){
        LCD.write(text);
    }

    public static void clearScreen(){
        LCD.clear();
    }

    //Set cursor line false for first line and true for second line
    public static void setCursorLine(boolean line){
        LCD.cursor(line?1:0,0);
    }

}
