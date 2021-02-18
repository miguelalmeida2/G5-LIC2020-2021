import java.lang.Math;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class RouletteGameApp {

    private static final int MAX_BET = 9;
    private static final int COIN_VALUE = 2;
    private static final int MIN_ROL_NUM = 0;
    private static final int MAX_ROL_NUM = 9;
    private static final int MAINTENANCE_COINS = 100;

    private static final int MAINTENANCE_BUTTON = 0x80;
    static int[] betsWon = {0,0,0,0,0,0,0,0,0,0};
    static int[] betsWonValue = {0,0,0,0,0,0,0,0,0,0};
    private static final int[] currentBets = {0,0,0,0,0,0,0,0,0,0};
    public static final String[] KEYOPTIONS = {"0-Stats #-Count ", "*-Play  8-ShutD "};

    private static int totalCoins = 0;
    private static int coinsAvailable = 0;
    private static int rouletteNumber;

    public static final int WAIT_TIME_5SEC = 5000; //5seg

    public static void main(String[] args){
        init();
        gameRotation(false);
    }

    private static void init(){
        HAL.init();
        KBD.init();
        LCD.init();
        RouletteDisplay.init();
        TUI.init();
        Statistics.init(); // Load's previous Statistics from Statistics.txt file
    }

    public static int[] specialChar =
            {0,0b00011111,0b00010001,0b00010101,0b00010001,0b00011111,0,0,   // 0
             0,0b00011111,0b00010101,0b00010001,0b00010101,0b00011111,0,0,   // 1
             0,0b00011111,0b00010011,0b00010101,0b00011001,0b00011111,0,0};  // 2

    private static void gameRotation(boolean maintenance){
        while(true) {
            coinsAvailable = (maintenance)? MAINTENANCE_COINS : totalCoins;
            if(!maintenance){firstMenu();
            while (coinsAvailable == 0){
                if (CoinAcceptor.checkForInsertedCoin()) addCoin();
            }
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

    private static void firstMenu(){
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

    private static void betsMenu(){
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

    private static void rouletteRoll(){
        rouletteNumber= (int)(Math.random()*(MAX_ROL_NUM - MIN_ROL_NUM +1));
        RouletteDisplay.animationRotatingSegment();
    }

    private static void updateTotalCoins(){
        TUI.setCursor(1,14-TUI.digitDim(coinsAvailable));
        TUI.write(" $" + coinsAvailable);
    }

    public static int addCoin() {
        coinsAvailable += COIN_VALUE;
        updateTotalCoins();
        return coinsAvailable;
    }

    private static void coinPlacedOnBets(){
        coinsAvailable -= 1;
    }

    private static void placeBet(int bet){
        LCD.cursor(0,bet);
        if(bet>=0 && currentBets[bet]<MAX_BET && coinsAvailable>0){
            coinPlacedOnBets();
            TUI.write(String.valueOf(++currentBets[bet]));
        }
    }

    private static void clearPlacedBets(){ for(int n=0;n<=9;n++) currentBets[n] = 0; }

    private static void calculateWinsAndLosses(){
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

    private static char readKey(){
        char key = 0;
        while (key == 0) key = KBD.getKey();
        return key;
    }

    public static void waitforKey(char keyExpected){
        char key = 0;
        while (key != keyExpected) key = KBD.getKey();
    }

    private static void waitforPlay(){
        char key = 0;
        while (key != '*'){
            if (CoinAcceptor.checkForInsertedCoin()) addCoin();
            key = KBD.getKey();
            checkIfMaintenanceButtonOn();
        }
    }

    private static void checkIfMaintenanceButtonOn(){
        if(HAL.readBits(MAINTENANCE_BUTTON) == MAINTENANCE_BUTTON) { maintenanceOptions(M.maintenanceMenu());}
    }

    public static void checkIfMaintenanceButtonOff(){
        if(HAL.readBits(MAINTENANCE_BUTTON) != MAINTENANCE_BUTTON) gameRotation(false);
    }

    private static void maintenanceOptions(char pressed){
        if(pressed == '0') {
            TUI.clearScreen();
            int line = 1;
            TUI.write(""+(line-1)+": -> "+betsWon[line-1]+" $:"+betsWonValue[line-1],0,0);
            TUI.write(""+line+": -> "+betsWon[line]+" $:"+betsWonValue[line],1,0);
            char key = KBD.waitKey(WAIT_TIME_5SEC);
            do {
                if(key == '2' && line > 1) --line;
                if(key == '8' && line < 9) ++line;
                if(key != '8' && key != '2') break;
                TUI.write(""+(line-1)+": -> "+betsWon[line-1]+" $:"+betsWonValue[line-1],0,0);
                TUI.write(""+line+": -> "+betsWon[line]+" $:"+betsWonValue[line],1,0);
                key = KBD.waitKey(WAIT_TIME_5SEC);
            }while(key != 0);
        }else if(pressed == '#'){
            TUI.clearScreen();
            TUI.write("Games: " + Statistics.getGames(),0,0);
            TUI.write("Coins: " + Statistics.getCoins(),1,0);
            char key = KBD.waitKey(WAIT_TIME_5SEC);
        }else if(pressed == '*') gameRotation(true);
        else if(pressed == '8') shutdownMenu();
        checkIfMaintenanceButtonOn();
    }

    private static void shutdownMenu(){
        TUI.write("    Shutdown    ",0,0);
        TUI.write("5-Yes  other-No ",1,0);
        char key = KBD.waitKey(WAIT_TIME_5SEC);
        if (key == '5') {
            Statistics.save();  //Saves scores and stats to Statistics.txt file
            System.exit(0);

        }M.maintenanceMenu();
    }
}