import java.lang.Math;
import isel.leic.utils.*;

// Controla o Roulette Display.
public class RouletteDisplay {

    private static final int MAX_ANIMATION_TIME = 20;
    private static final int MIN_ANIMATION_TIME = 7;

    private static final int WR_BIT = 0x40;
    private static final int ANIM_BIT = 0x0a;
    private static final int DISPLAY_OFF = 0x1c;

    private static final int WAIT_TIME = 300;
    private static final int WAIT_TIME_NUMBER = 200;
    private static final int WAIT_TIME_HALF_SECOND = 500;

    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init() {
        clearDisplay();
    }

    // Envia comando para apresentar o número sorteado
    public static void showNumber(int number) {
        HAL.clrBits(0xff);
        HAL.setBits(number);
        HAL.setBits(WR_BIT);
    }

    // Envia comando de animação
    public static void animation(int rouletteNumber) {
        int animationDuration = (int) (Math.random() * (MAX_ANIMATION_TIME - MIN_ANIMATION_TIME + 1) * 1000);
        int stopAnimationTime = (int) (Time.getTimeInMillis() + animationDuration);
        int i;
        while (true) {
            for (i = 0; i < 6 & stopAnimationTime > (int)Time.getTimeInMillis(); i++) {
                showNumber(ANIM_BIT + i);
                Time.sleep(WAIT_TIME);
            }if(stopAnimationTime < (int)Time.getTimeInMillis()) break;
        }
        int n=0;
        while (true) {
            for (; n < 10; n++) {
                showNumber(n);
                Time.sleep(WAIT_TIME_NUMBER);
                if(rouletteNumber == n) {
                    Time.sleep(WAIT_TIME);
                    break;
                }
            }if(rouletteNumber == n) break;
        }
    }

    public static void blinkNumber(int number){
        for(int i=0; i < 10;i++){
            Time.sleep(WAIT_TIME_HALF_SECOND);
            showNumber(DISPLAY_OFF);
            Time.sleep(WAIT_TIME_NUMBER);
            showNumber(number);
        }
    }

    public static void clearDisplay(){
        showNumber(DISPLAY_OFF);
    }
}