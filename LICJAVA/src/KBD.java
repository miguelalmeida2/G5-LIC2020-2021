public class KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.

    private final static int KVAL_MASK = 0x10; // 0001 0000
    private final static int ACK_MASK = 0x80;  // 1000 0000
    private final static int KBD_MASK = 0x0F;  // 0000 1111
    private final static char[] keyboard_simulation = {'1','4','7','*','2','5','8','0','3','6','9','#'};
    private final static char[] keyboard_hardware = {'1','2','3','4','5','6','7','8','9','*','0','#'};

    private static char[] keyboard;

    // Inicia a classe
    public static void init() {
        keyboard = keyboard_simulation;
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    public static char getKey() {
        char key = 0;
        if(HAL.isBit(KVAL_MASK)) {
            key = keyboard[KBD_MASK & HAL.readBits(KBD_MASK)];
            HAL.setBits(ACK_MASK);
            while (HAL.isBit(KVAL_MASK)) ;
            HAL.clrBits(ACK_MASK);
        }return key;
    }

    // Retorna quando a tecla é premida ou NONE após decorrido ‘timeout’ milisegundos.
    public static char waitKey(long timeout) {
        timeout += System.currentTimeMillis();
        char key;
        do {
            key = getKey();
            if (key != 0) return key;
        } while (System.currentTimeMillis() < timeout);
        return 0;
    }
}
