public class TUI {

    public static void main(String[] args){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();
        init();
    }

    public static int[] specialChar = {0b00000111,0b00001000,0b00011110,0b00001000,0b00011110,0b00001000,0b00000111,0b00000000};


    public static void init(){

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

    public static int digitDim(int digit){
        int spaces = 0;
        if(digit < 10) spaces = 1;
        else if(digit < 100) spaces = 2;
        else spaces = 3;
        return spaces;
    }

    public static void setCursor(int line, int col){
        LCD.setCursor(line, col);
    }

    public static void specialChar(){

    }

    //create write on center


    //create enable and disable cursor

}
