public class TUI {

    private static final int OFFSET = -1;

    public static int[] specialChar =
            {0,0b00011111,0b00010001,0b00010101,0b00010001,0b00011111,0,0,  // 0
            0,0b00011111,0b00010101,0b00010001,0b00010101,0b00011111,0,0,   // 1
            0,0b00011111,0b00010011,0b00010101,0b00011001,0b00011111,0,0};  // 2


    public static void init(){
        //grava carateres especiais
        LCD.specialChar(0);
        LCD.specialChar(1);
        LCD.specialChar(2);
        displayCursor(false);
    }

    public static void write(String text){
        LCD.write(text);
        displayCursor(false);
    }

    public static void write(String text, int line, int col){
        LCD.cursor(line,col);
        LCD.write(text);
        displayCursor(false);
    }

    public static void writeOnCenter(String txt, int line){
        setCursor(line,0);
        int i = 0;
        for(;LCD.COLS >= txt.length()+i*2;++i);
        if(LCD.COLS == txt.length()+i*2) TUI.write(txt,line,i);
        else TUI.write(txt,line,i+OFFSET);   //caso valor seja impar offset txt +1 para a direita
    }

    public static void clearScreen(){
        LCD.clear();
    }

    public static int digitDim(int digit){
        int spaces;
        if(digit < 10) spaces = 1;
        else if(digit < 100) spaces = 2;
        else spaces = 3;
        return spaces;
    }

    public static void setCursor(int line, int col){
        LCD.setCursor(line, col);
    }

    public static void displayCursor(boolean cursor){
        LCD.displayCursor(cursor);
    }
}
