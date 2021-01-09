import java.lang.Math;

public class RouletteGameApp {

    private static final int MAX_BET = 9;
    private static final int COIN_VALUE = 2;
    private static final int MAINTENANCE_COINS = 100;
    private static final int MIN_ROL_NUM = 0;
    private static final int MAX_ROL_NUM = 9;

    public static int MAINTENANCE_BUTTON = 0x80;

    public static int[] currentBets = {0,0,0,0,0,0,0,0,0,0};

    private static int totalCoins = 10;
    private static int coinsAvailable = 0;
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

    public static void gameRotation(boolean maintenance){
        while(true) {
            coinsAvailable = (maintenance)? MAINTENANCE_COINS : totalCoins;
            firstMenu();
            while (coinsAvailable == 0) ;
            if(maintenance) waitforKey('*');
            else waitforPlay();
            betsMenu();
            char currentKey = 0;
            while (true) {
                currentKey = readKey();
                if (currentKey == '#') {
                    rouletteRoll();
                    calculateWinsAndLosses();
                    if(!maintenance) totalCoins = coinsAvailable;
                    break;
                }placeBet((int)(currentKey - '0'));
                updateTotalCoins();
            }
            clearPlacedBets();
            RouletteDisplay.clearDisplay();
            if(maintenance) M.maintenanceMenu();
        }
    }

    public static void firstMenu(){
        TUI.clearScreen();
        TUI.write(" Roulette Game  ",0,0);
        TUI.setCursor(1,0);
        for(int i=0;i<3;i++){
            TUI.write(" " + ((char)(i+'1')) + " ");
            LCD.customChar(i);
        }
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
        RouletteDisplay.animation(rouletteNumber);
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
            TUI.write(String.valueOf(++currentBets[bet]));
        }
    }

    public static void clearPlacedBets(){ for(int n=0;n<=9;n++) currentBets[n] = 0; }

    public static void calculateWinsAndLosses(){
        int won = 0, lost = 0, coinsWonLoss;
        String winOrLoss;
        for(int n=0;n<=9;n++){
            if(n == rouletteNumber) won = currentBets[n];
            else if(currentBets[n]>0) lost += currentBets[n];
        }
        if(won!=0) won*=2;
        coinsWonLoss = won-lost;
        coinsAvailable += won;
        winOrLoss = (coinsWonLoss > 0)?"W":"L";
        coinsWonLoss = Math.abs(coinsWonLoss);
        TUI.setCursor(0, 14-TUI.digitDim(coinsWonLoss));
        TUI.write(winOrLoss + "$" + coinsWonLoss);
        RouletteDisplay.blinkNumber(rouletteNumber);
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
            checkIfMaintenanceButtonOn();
        }
    }

    public static void checkIfMaintenanceButtonOn(){ if(HAL.readBits(MAINTENANCE_BUTTON) == MAINTENANCE_BUTTON) M.maintenanceMenu(); }

    public static void checkIfMaintenanceButtonOff(){ if(HAL.readBits(MAINTENANCE_BUTTON) != MAINTENANCE_BUTTON) RouletteGameApp.gameRotation(false); }
}