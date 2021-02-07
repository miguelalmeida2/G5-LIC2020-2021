

public class SerialEmitter { // Envia tramas para o módulo Serial Receiver.
    public enum Destination {RDisplay,LCD};
    private static int SDX;
    private static final int SOCSEL_MASK = 0x08;
    private static final int SDX_MASK = 0x02;
    private static final int CLOCK_MASK = 0x04;
    // Inicia a classe
    public static void init(){
        HAL.writeBits(0x0F, 0);
    }

    // Envia uma trama para o Serial Receiver identificando o destino em addr e os bits de dados em‘data’.
    public static void send(Destination addr, int data){
        init();
        int p = 0;
        int value;
        int LnD = (addr.ordinal() == Destination.RDisplay.ordinal()) ? 0x00 : 0x01;
        SDX = (data << 1) | LnD;

        HAL.setBits(SOCSEL_MASK);
        for (int i = 0; i <= 5; ++i){
            value = SDX & 0x01;
            if (value == 0x01){
                HAL.setBits(SDX_MASK);
                ++p;
            }
            SCLK();
            HAL.clrBits(SDX_MASK);
            SDX = SDX >> 1;
        }

        if ( p % 2 != 0) HAL.setBits(SDX_MASK);
        SCLK();
        HAL.clrBits(SDX_MASK);
        HAL.clrBits(SOCSEL_MASK);
    }
    private static void SCLK(){
        HAL.setBits(CLOCK_MASK);
        HAL.clrBits(CLOCK_MASK);
    }
}