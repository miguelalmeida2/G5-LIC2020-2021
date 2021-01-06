public class TUI {

    public static void main(String[] args){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();
        init();
    }

    public static void init(){

    }

    public static char readBets(){
        char key = 0;
        while (key == 0){
            key = KBD.getKey();
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

    public static void write(String text, int line, int col){
        LCD.cursor(line,col);
        LCD.write(text);
    }

    public static void clearScreen(){
        LCD.clear();
    }

    //Set cursor line false for first line and true for second line
    public static void setCursorLine(boolean line){
        LCD.cursor(line?1:0,0);
    }

    public static String coinDigitDim(){
        String spaces = "";
        if(RouletteGameApp.totalCoins < 10) spaces = "  ";
        else if(RouletteGameApp.totalCoins < 100) spaces = " ";
        //preciso de considerar superior a 999???
        return spaces;
    }



}
