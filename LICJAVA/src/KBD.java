import isel.leic.UsbPort;

public class KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.

    public static void main(String[] args){
        HAL.init();
        KBD.init();
        char key;
        HAL.clrBits(ACK_MASK);
        while (true){
            key = getKey();
            if (key != NONE) System.out.println("k " + key);
        }
    }

    private static final char NONE = 0;
    private static char Key = NONE;
    private final static int KVAL_MASK = 0x10; // 0001 0000
    private final static int ACK_MASK = 0x80;  // 1000 0000
    private final static int KBD_MASK = 0x0F;  // 0000 1111
    private final static char[] keyboard_simulation = {'1','4','7','*','2','5','8','0','3','6','9','#'};
    private final static char[] keyboard_hardware = {'1','2','3','4','5','6','7','8','9','*','0','#'};

    private static char[] keyboard;

    // Inicia a classe
    public static void init() {
        boolean simulation = true;
        keyboard = (simulation? keyboard_simulation :keyboard_hardware);
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    public static char getKey() {
        char key = NONE;
        if(HAL.isBit(KVAL_MASK)) { //verifica se Kval ativo
            int keyopposite = KBD_MASK & ~HAL.readBits(KBD_MASK);
            if (keyopposite >= 12 || keyopposite < 0) return key;

            //if (key >= 12 || key < 0) return key;

            System.out.println("bits " + HAL.readBits(KBD_MASK) + "   !bits " + keyopposite);
            //HAL.clrBits(ACK_MASK);

            key = keyboard[keyopposite];
            HAL.setBits(ACK_MASK); //ativa acknowledge

            while (HAL.isBit(KVAL_MASK)) ;
            HAL.clrBits(ACK_MASK);
        }return key;
    }

    // Retorna quando a tecla é premida ou NONE após decorrido ‘timeout’ milisegundos.
    public static char waitKey(long timeout) {
        timeout += System.currentTimeMillis();
        Key = NONE;
        do {
            Key=getKey();
            if (Key!=NONE) return Key;
        } while (System.currentTimeMillis()<timeout);
        return NONE;
    }
}
