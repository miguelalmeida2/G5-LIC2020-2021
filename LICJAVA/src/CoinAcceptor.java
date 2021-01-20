public class CoinAcceptor {

    private final static int COIN_MASK=0x40;
    private final static int COIN_ACCEPT_MASK=0x40;

    public static boolean checkForInsertedCoin(){
        if(HAL.isBit(COIN_MASK)) {
            HAL.setBits(COIN_ACCEPT_MASK);
            HAL.clrBits(COIN_ACCEPT_MASK);
            return true;
        }
        return false;
    }
}

