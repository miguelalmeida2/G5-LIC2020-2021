import isel.leic.UsbPort;
import isel.leic.utils.*;

public class KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    public static final char NONE = 0;
    public static char Key = NONE;

    // Inicia a classe
    public static void init() {
        HAL.init();
        while (true)
            Key = waitKey(500);
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    public static char getKey() {
        switch (HAL.readBits(0x0F)) {
            case 0:
                return '1';
            case 1:
                return '2';
            case 2:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 8:
                return '7';
            case 9:
                return '8';
            case 12:
                return '*';
            case 13:
                return '0';
            case 14:
                return '#';
            default:
                return NONE;
        }
    }

    // Retorna quando a tecla é premida ou NONE após decorrido ‘timeout’ milisegundos.
    public static char waitKey(long timeout) {
        long time = Time.getTimeInMillis();
        while (time - Time.getTimeInMillis() < timeout) {
            return getKey();
        }
        return NONE;
    }
}


/**

 1 2 3
 4 5 6
 7 8 9
 * 0 #

 **/