import isel.leic.utils.*;

import java.security.SecureRandom;

public class RouletteDisplay { // Controla o Roulette Display.
    public static boolean FoundNumber = false;
    public static int luckyNumber = 5;

    public static void main(String[] args){
        HAL.init();
        KBD.init();
        LCD.init();
        TUI.init();
        init();

        SerialEmitter.init();
        HAL.writeBits(0x0F, 1);
        animation();

    }

    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init() {

    }
    // Envia comando para apresentar o número sorteado
    public static void showNumber(int number) {
        SerialEmitter.send(SerialEmitter.Destination.RDisplay,number);
    }



    // Envia comando de animação
    public static void animation() {
        int i=3;

        showNumber(i);
        Time.sleep(250);
      /*while (!FoundNumber && i<9) {
          for (i = 0; i < 10; i++) {
              Time.sleep(250);
              showNumber(i);
          }
      }*/
      showNumber(luckyNumber);
      Time.sleep(100000);
      }

}