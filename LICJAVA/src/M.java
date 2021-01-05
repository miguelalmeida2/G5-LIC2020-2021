public class M {

    public static int COIN_VALUE = 2;
    public static int totalCoins = 0;

    public static int coin() {
        return totalCoins += COIN_VALUE;
    }

    public static String coinDigitDim(){
        String spaces = "";
        if(totalCoins < 10) spaces = "  ";
        else if(totalCoins < 100) spaces = " ";
        //preciso de considerar superior a 999???
        return spaces;
    }
}
