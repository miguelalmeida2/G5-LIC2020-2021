import java.lang.Math;
import isel.leic.utils.*;

// Controla o Roulette Display.
public class RouletteDisplay {

    private static final int MAX_ANIMATION_TIME = 8;
    private static final int MIN_ANIMATION_TIME = 7;

    private static final int WR_BIT = 0x40;
    private static final int ANIM_BIT = 0x0a;
    private static final int DISPLAY_OFF = 0x1c;

    private static final int WAIT_TIME = 100;
    private static final int WAIT_TIME_NUMBER = 200;

    public static void main(String[] args){
        //init tem prioridade para não apresentar 0 no display de 7 segmentos
        HAL.init();
        KBD.init();
        LCD.init();
        TUI.init();

        init();
        int rouletteNumber= (int)(Math.random()*(10));
        System.out.println(rouletteNumber);
        animation(rouletteNumber);
    }

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
        }i=0;
        int n=0;
        while (i < 2) {
            for (; n < 10; n++) {
                showNumber(n);
                Time.sleep(WAIT_TIME_NUMBER);
                if(rouletteNumber == n) {
                    Time.sleep(300);
                    break;
                }
            }if(rouletteNumber == n) break;
        }
    }

    public static void blinkNumber(int number){
        for(int i=0; i < 10;i++){
            Time.sleep(500);
            showNumber(DISPLAY_OFF);
            Time.sleep(WAIT_TIME_NUMBER);
            showNumber(number);
        }
    }

    public static void clearDisplay(){
        showNumber(DISPLAY_OFF);
    }
}