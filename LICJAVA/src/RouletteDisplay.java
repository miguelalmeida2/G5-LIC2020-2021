import isel.leic.UsbPort;
import isel.leic.utils.*;

import java.security.SecureRandom;

public class RouletteDisplay { // Controla o Roulette Display.
    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init() {

    }
    // Envia comando para apresentar o número sorteado
    public static void showNumber(int number) {
        SerialEmitter.send(SerialEmitter.Destination.RDisplay,number);
    }


    // Envia comando de animação
    /** public static void animation() {
      while (!FoundNumber) {
          for (int i = 0; i < 10; i++) {
              Time.sleep(250);
              showNumber(i);
          }
      }
      showNumber(luckyNumber);
      }
     **/
}