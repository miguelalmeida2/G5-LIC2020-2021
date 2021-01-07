import java.lang.Math;
import isel.leic.utils.Time;


public class RouletteGameApp {

    public static int[] currentBets = {0,0,0,0,0,0,0,0,0,0};

    public static int MAX_BET = 9;
    public static int COIN_VALUE = 2;
    public static int MIN_ROL_NUM = 0;
    public static int MAX_ROL_NUM = 9;
    public static int TIME_SHOW_RESULTS = 750;

    public static int totalCoins = 10;
    public static int rouletteNumber;


    public static void main(String[] args){
        init();
        while(true) {
            firstMenu();
            while (totalCoins == 0) ;

            TUI.waitforKey('*');
            betsMenu();

            char currentKey = 0;
            while (true) {
                currentKey = TUI.readBets();
                if (currentKey == '#') {
                    rouletteRoll();
                    calculateWinsAndLosses();
                    break;
                }
                placeBet((int) (currentKey - '0'));
                updateTotalCoins();
            }
            Time.sleep(TIME_SHOW_RESULTS);
            clearPlacedBets();
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
        TUI.setCursor(1,0);
        TUI.write(" 1 X 2 X 3 X");
        TUI.setCursor(1,15-TUI.digitDim(totalCoins));
        TUI.write("$" + totalCoins);
    }

    public static void betsMenu(){
        TUI.clearScreen();
        TUI.setCursor(1,0);
        TUI.write("0123456789  ");
        TUI.setCursor(1,15-TUI.digitDim(totalCoins));
        TUI.write("$" + totalCoins);
    }

    public static void rouletteRoll(){
        rouletteNumber= (int)(Math.random()*(MAX_ROL_NUM - MIN_ROL_NUM +1));
        System.out.println(rouletteNumber);

    }

    public static void updateTotalCoins(){
        TUI.setCursor(1,14-TUI.digitDim(totalCoins));
        TUI.write(" $" + totalCoins);
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

    public static void clearPlacedBets(){ for(int n=0;n<=9;n++) currentBets[n] = 0; }

    public static void calculateWinsAndLosses(){
        int won = 0, lost = 0, coinsWonLoss;
        String winOrLoss;
        for(int n=0;n<=9;n++){
            if(n == rouletteNumber) won = currentBets[n];
            else if(currentBets[n]>0) lost += currentBets[n];
        }
        totalCoins += won*2;

        coinsWonLoss = won-lost;
        winOrLoss = (coinsWonLoss > 0)?"W":"L";
        coinsWonLoss = Math.abs(coinsWonLoss);
        TUI.setCursor(0, 14-TUI.digitDim(coinsWonLoss));
        TUI.write(winOrLoss + "$" + coinsWonLoss);
    }

}
