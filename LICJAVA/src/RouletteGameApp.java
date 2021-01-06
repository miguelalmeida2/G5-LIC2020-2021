public class RouletteGameApp {

    public static int[] currentBets = {0,0,0,0,0,0,0,0,0,0};
    public static int MAX_BET = 9;

    public static int COIN_VALUE = 2;
    public static int totalCoins = 10;


    public static void main(String[] args){
        init();
        firstMenu();
        while(totalCoins == 0);

        TUI.waitforKey('*');
        betsMenu();

        char currentKey = 0;
        while (true){
            currentKey = TUI.readBets();
            if (currentKey == '#'){
                rouletteRoll();
                break;
            }
            placeBet((int)(currentKey-'0'));
            updateTotalCoins();
        }
    }

    public static void init(){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();
        TUI.init();

    }

    public static void firstMenu(){
        TUI.clearScreen();
        TUI.write(" Roulette Game  ");
        TUI.setCursorLine(true);
        TUI.write(" 1 X 2 X 3 X" + TUI.coinDigitDim() + "$" + totalCoins);
    }

    public static void betsMenu(){
        TUI.clearScreen();
        TUI.setCursorLine(true);
        TUI.write("0123456789  " + TUI.coinDigitDim() + "$" + totalCoins);
    }

    public static void rouletteRoll(){

    }

    public static void updateTotalCoins(){
        TUI.write(TUI.coinDigitDim() + "$" + totalCoins,1,12);
    }


    public static int addCoin() {
        return totalCoins += COIN_VALUE;
    }

    public static void coinPlacedOnBets(){
        totalCoins -= 1;
    }

    public static void placeBet(int bet){
        LCD.cursor(0,bet);
        if(bet>=0 && currentBets[bet]<MAX_BET && totalCoins>0){
            coinPlacedOnBets();
            TUI.write(String.valueOf(++currentBets[bet]));}
    }

    public static void clearPlacedBets(){
        for(int n=0;n<=10;n++){
            currentBets[n] = 0;
        }
    }

    public static void calculateWinsAndLosses(){

    }

}
