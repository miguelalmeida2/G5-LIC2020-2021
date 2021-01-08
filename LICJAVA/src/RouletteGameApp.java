import java.lang.Math;
import isel.leic.utils.Time;


public class RouletteGameApp {

    public static int[] currentBets = {0,0,0,0,0,0,0,0,0,0};

    public static int MAX_BET = 9;
    public static int COIN_VALUE = 2;
    public static int MAINTAINANCE_COINS = 100;
    public static int MIN_ROL_NUM = 0;
    public static int MAX_ROL_NUM = 9;
    public static int WAIT_TIME = 5000; //5seg

    public static int MAINTAINANCE_BUTTON = 0xe0;

    public static int totalCoins = 10;
    public static int coinsAvailable = 0;
    public static int rouletteNumber;


    public static void main(String[] args){
        init();
        gameRotation(false);
    }

    public static void init(){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();
        TUI.init();
    }

    public static void gameRotation(boolean maintainance){
        while(true) {
            coinsAvailable = (maintainance)? MAINTAINANCE_COINS : totalCoins;
            firstMenu();
            while (coinsAvailable == 0) ;
            waitforPlay();
            betsMenu();

            char currentKey = 0;
            while (true) {
                currentKey = readKey();
                if (currentKey == '#') {
                    rouletteRoll();
                    calculateWinsAndLosses();
                    break;
                }placeBet((int)(currentKey - '0'));
                updateTotalCoins();
            }
            Time.sleep(WAIT_TIME);
            clearPlacedBets();
            if(maintainance) break;
        }
    }

    public static void firstMenu(){
        TUI.clearScreen();
        TUI.write(" Roulette Game  ");
        TUI.setCursor(1,0);
        TUI.write(" 1 X 2 X 3 X");
        TUI.setCursor(1,15-TUI.digitDim(coinsAvailable));
        TUI.write("$" + coinsAvailable);
    }

    public static void betsMenu(){
        TUI.clearScreen();
        TUI.setCursor(1,0);
        TUI.write("0123456789  ");
        TUI.setCursor(1,15-TUI.digitDim(coinsAvailable));
        TUI.write("$" + coinsAvailable);
    }

    public static void rouletteRoll(){
        rouletteNumber= (int)(Math.random()*(MAX_ROL_NUM - MIN_ROL_NUM +1));
        System.out.println(rouletteNumber);

    }

    public static void updateTotalCoins(){
        TUI.setCursor(1,14-TUI.digitDim(coinsAvailable));
        TUI.write(" $" + coinsAvailable);
    }

    public static int addCoin() {
        return totalCoins += COIN_VALUE;
    }

    public static void coinPlacedOnBets(){
        coinsAvailable -= 1;
    }

    public static void placeBet(int bet){
        LCD.cursor(0,bet);
        if(bet>=0 && currentBets[bet]<MAX_BET && coinsAvailable>0){
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
        }coinsAvailable += won;
        if(won!=0) won++;
        coinsWonLoss = won-lost;
        winOrLoss = (coinsWonLoss > 0)?"W":"L";
        coinsWonLoss = Math.abs(coinsWonLoss);
        TUI.setCursor(0, 14-TUI.digitDim(coinsWonLoss));
        TUI.write(winOrLoss + "$" + coinsWonLoss);
    }

    public static char readKey(){
        char key = 0;
        while (key == 0) key = KBD.getKey();
        return key;
    }

    public static void waitforKey(char keyExpected){
        char key = 0;
        while (key != keyExpected) key = KBD.getKey();
    }

    public static void waitforPlay(){
        char key = 0;
        while (key != '*'){
            key = KBD.getKey();
            checkIfMaintainanceButtonOn();
        }
    }


    //Problemas com entrada e saída do modo manutenção devido ao botão

    public static void checkIfMaintainanceButtonOn(){
        if(HAL.readBits(0xff) == MAINTAINANCE_BUTTON) M.maintainanceMenu();
    }

    public static void checkIfMaintainanceButtonOff(){
        if(HAL.readBits(0xff) != MAINTAINANCE_BUTTON) RouletteGameApp.gameRotation(false);
    }

}
