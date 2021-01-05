public class RouletteGameApp {

    public static int[] currentBets = {0,0,0,0,0,0,0,0,0,0};
    public static int MAX_BET = 9;


    public static void main(String[] args){
        init();

    }

    public static void init(){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();

        TUI.init();

    }


    public static void placeBet(int bet){
        LCD.cursor(0,bet);
        if(bet>0 && currentBets[bet]<=MAX_BET){
        TUI.write(String.valueOf(currentBets[bet]++));}
    }

    public static void clearPlacedBets(){
        for(int n=0;n<=10;n++){
            currentBets[n] = 0;
        }
    }

    public static void calculateWinsAndLosses(){

    }
}
