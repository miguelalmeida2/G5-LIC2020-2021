

public class SerialEmitter { // Envia tramas para o módulo Serial Receiver.
    public static enum Destination {RDisplay,LCD};
    private static int SDX;
    private static int SOCsel = 0x02;
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

        HAL.setBits(SOCsel);
        for (int i = 0; i <= 5; ++i){
            value = SDX & 0x01;
            if (value == 0x01){
                HAL.setBits(0x01);
                ++p;
            }
            SCLK();
            HAL.clrBits(0x01);
            SDX = SDX >> 1;
        }

        if ( p % 2 != 0) HAL.setBits(0x01);
        SCLK();
        HAL.clrBits(0x01);
        HAL.clrBits(SOCsel);
    }
    private static void SCLK(){
        HAL.setBits(0x03);
        HAL.clrBits(0x03);
    }
}