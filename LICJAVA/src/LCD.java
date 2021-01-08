import isel.leic.utils.Time;

public class LCD { // Escreve no LCD usando a interface a 4 bits.

    public static void main(String[] args){
        HAL.init();
        init();
        specialChar();
    }

    private static final int LINES = 2, COLS = 16; // Dimensão do display.
    private static final int NIBBLE_SIZE = 4;

    // Define se a interface com o LCD é série ou paralela
    private static final boolean SERIAL_INTERFACE = false;

    // Envia a sequência de iniciação para comunicação a 4 bits.
    public static void init() {
        Time.sleep(15);
        writeNibble(false,0x03);
        Time.sleep(5);
        writeNibble(false,0x03);
        Time.sleep(1);
        writeNibble(false,0x03);
        writeNibble(false,0x02);
        writeCMD(0x28);
        writeCMD(0x08);
        writeCMD(0x01);
        writeCMD(0x06);
        writeCMD(0x0F);
    }

    // Escreve um nibble de comando/dados no LCD em paralelo
    private static void writeNibbleParallel(boolean rs, int data) {
        //only works if data is denied
        if(rs){
            HAL.writeBits(0x10,0x10);
        }else{
            HAL.writeBits(0x10,0);
        }
        HAL.writeBits(0x20,0x20);
        HAL.writeBits(0x0F,data);
        HAL.writeBits(0x20,0);
    }

    // Escreve um nibble de comando/dados no LCD em série
    private static void writeNibbleSerial(boolean rs, int data) { }

    // Escreve um nibble de comando/dados no LCD
    private static void writeNibble(boolean rs, int data) {
        if(!SERIAL_INTERFACE)
            writeNibbleParallel(rs,data);
        else writeNibbleSerial(rs,data);
    }

    // Escreve um byte de comando/dados no LCD
    private static void writeByte(boolean rs, int data) {
         int highData = ((data >>> NIBBLE_SIZE) & 0x0F); //Parte Alta do data
         int lowData = (data & 0x0F);                    //Parte Baixa do data
         writeNibble(rs,highData);
         writeNibble(rs,lowData);
    }

    // Escreve um comando no LCD
    private static void writeCMD(int data) {
        writeByte(false,data);
    }

    // Escreve um dado no LCD
    private static void writeDATA(int data) {
        writeByte(true,data);
    }

    // Escreve um caráter na posição corrente.
    public static void write(char c) {
        writeDATA((int)c);
        Time.sleep(25);
    }

    // Escreve uma string na posição corrente.
    public static void write(String txt) {
        for (int i = 0; i < txt.length(); i++) {
            write(txt.charAt(i));
        }
    }

    // Envia comando para posicionar cursor (‘lin’:0..LINES-1 , ‘col’:0..COLS-1)
    public static void cursor(int lin, int col) {
        writeCMD(0x80 + (lin==1?0x40:0x00) + col);
    }

    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    public static void clear() {
        writeCMD(0x01); // Clear Display e Coloca o cursor na posicao 0,0
    }

    public static void setCursor(int line, int col){
        cursor(line, col);
    }

    public static void specialChar(){
        //writeCMD(0x30);
        writeCMD( 0x40+(1*8));
        for(int i = 0; i < 8; i++) writeByte(true,TUI.specialChar[i]);
        writeByte(true,1);
    }

        /*writeByte(true,0b00000111);
        writeByte(true,0b00001000);
        writeByte(true,0b00011110);
        writeByte(true,0b00001000);

        writeByte(true,0b00011110);
        writeByte(true,0b00001000);
        writeByte(true,0b00000111);
        writeByte(true,0b00000000);*/

        //writeByte(true,1);
        //}
}