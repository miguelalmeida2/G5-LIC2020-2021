import java.lang.Math;

public class RouletteGameApp {

    private static final int MAX_BET = 9;
    private static final int COIN_VALUE = 2;
    private static final int MIN_ROL_NUM = 0;
    private static final int MAX_ROL_NUM = 9;
    private static final int MAINTENANCE_COINS = 100;

    private static final int MAINTENANCE_BUTTON = 0x80;

    private static final int[] currentBets = {0,0,0,0,0,0,0,0,0,0};
    public static final String[] KEYOPTIONS = {"0-Stats #-Count ", "*-Play  8-ShutD "};

    private static int totalCoins = 10;
    private static int coinsAvailable = 0;
    private static int rouletteNumber;

    public static final int WAIT_TIME_5SEC = 5000; //5seg


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

    public static int[] specialChar =
            {0,0b00011111,0b00010001,0b00010101,0b00010001,0b00011111,0,0,  // 0
            0,0b00011111,0b00010101,0b00010001,0b00010101,0b00011111,0,0,   // 1
            0,0b00011111,0b00010011,0b00010101,0b00011001,0b00011111,0,0};  // 2

    public static void gameRotation(boolean maintenance){
        while(true) {
            coinsAvailable = (maintenance)? MAINTENANCE_COINS : totalCoins;
            if(!maintenance){firstMenu();
            while (coinsAvailable == 0) ;
                waitforPlay();
            }
            betsMenu();
            char currentKey;
            while (true) {
                currentKey = readKey();
                placeBet(currentKey - '0');
                updateTotalCoins();
                if (currentKey == '#') {
                    rouletteRoll();
                    RouletteDisplay.animationRotatingNumbers(rouletteNumber);
                    calculateWinsAndLosses();
                    if(!maintenance) totalCoins = coinsAvailable;
                    break;
                }
            }
            clearPlacedBets();
            RouletteDisplay.clearDisplay();
            if(maintenance) maintenanceOptions(M.maintenanceMenu());
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

    public static void bet(int time){
        char currentKey = KBD.waitKey(time);
        placeBet(currentKey - '0');
        updateTotalCoins();
    }

    public static void rouletteRoll(){
        rouletteNumber= (int)(Math.random()*(MAX_ROL_NUM - MIN_ROL_NUM +1));
        RouletteDisplay.animationRotatingSegment();
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
        TUI.write(winOrLoss + "$" + coinsWonLoss,0,14-TUI.digitDim(coinsWonLoss));
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

    public static void checkIfMaintenanceButtonOn(){
        if(HAL.readBits(MAINTENANCE_BUTTON) == MAINTENANCE_BUTTON) {
            maintenanceOptions(M.maintenanceMenu());}

    }

    public static void checkIfMaintenanceButtonOff(){ if(HAL.readBits(MAINTENANCE_BUTTON) != MAINTENANCE_BUTTON) gameRotation(false); }

    public static void maintenanceOptions(char pressed){
        if(pressed == '0') {

            //TO DO

        }else if(pressed == '#'){

            //TO DO

        }else if(pressed == '*') gameRotation(true);
        else if(pressed == '8') shutdownMenu();
        checkIfMaintenanceButtonOn();

    }

    private static void shutdownMenu(){
        TUI.write("    Shutdown    ",0,0);
        TUI.write("5-Yes  other-No ",1,0);
        char key = KBD.waitKey(WAIT_TIME_5SEC);
        if (key == '5') {
            System.exit(0);

            //save scores and stats here

        }M.maintenanceMenu();
    }
}