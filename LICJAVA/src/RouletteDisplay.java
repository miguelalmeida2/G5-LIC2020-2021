import java.lang.Math;
import isel.leic.utils.*;

// Controla o Roulette Display.
public class RouletteDisplay {

    private static final int MAX_ANIMATION_TIME_ROTATINGSEGMENT = 15;
    private static final int MIN_ANIMATION_TIME_ROTATINGSEGMENT = 5;
    private static final int MAX_ANIMATION_TIME_ROTATINGNUMBERS = 15;
    private static final int MIN_ANIMATION_TIME_ROTATINGNUMBERS = 5;
    private static final int LAST_NUMBER_SHOW_TIME = 5;
    private static final int LASTBUTONE_NUMBER_SHOW_TIME = 2;

    private static final int WR_BIT = 0x40;
    private static final int ANIM_BIT = 0x0a;
    private static final int DISPLAY_OFF = 0x1c;

    private static final int WAIT_TIME = 300;
    private static final int WAIT_TIME_NUMBER = 200;
    private static final int WAIT_TIME_HALF_SECOND = 500;
    private static final int WAIT_TIME_ONE_AND_HALF_SECOND = 1500;
    private static final int WAIT_TIME_TWO_AND_HALF_SECOND = 2500;

    private static final boolean SERIAL_INTERFACE = true;    // Define se a interface com o LCD é série ou paralela

    public static void main(String[] args) {

        HAL.init();
        init();
        showNumber(1);
        clearDisplay();
        showNumber(5);
        clearDisplay();
        showNumber(9);
        //animationRotatingSegment();
    }
    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init() {
        clearDisplay();
    }

    // Envia comando para apresentar o número sorteado
    public static void showNumber(int number) {
        if (SERIAL_INTERFACE){
            SerialEmitter.send(SerialEmitter.Destination.RDisplay, number);
        }else {
            HAL.clrBits(0xff);
            HAL.setBits(number);
            HAL.setBits(WR_BIT);
        }
    }

    public static void animationRotatingSegment(){
        int animationDuration = (int) (Math.random() * (MAX_ANIMATION_TIME_ROTATINGSEGMENT - MIN_ANIMATION_TIME_ROTATINGSEGMENT + 1) * 1000);
        int stopAnimationTime = (int) (Time.getTimeInMillis() + animationDuration);
        int i;
        while (true) {
            for (i = 0; i < 6 & stopAnimationTime > (int)Time.getTimeInMillis(); i++) {
                showNumber(ANIM_BIT + i);
                RouletteGameApp.bet(WAIT_TIME);
            }if(stopAnimationTime < (int)Time.getTimeInMillis()) break;
        }
    }

    public static void animationRotatingNumbers(int rouletteNumber) {
        int animationDuration = 1000;
        int animationTimeForFirstNumbers;
        if(rouletteNumber>=6) animationTimeForFirstNumbers = animationDuration/(rouletteNumber-3);
        else {
            animationTimeForFirstNumbers = animationDuration / (rouletteNumber + 10 - 3);
            animationCompleteRotation(rouletteNumber, animationTimeForFirstNumbers);
        }
        for(int i = 0;i <= rouletteNumber;++i) {
            if ((rouletteNumber-3) > 0) showNumberAnim(i,animationTimeForFirstNumbers);
            else if ((rouletteNumber-2) > 0) showNumberAnim(i,WAIT_TIME_HALF_SECOND);
            else if((rouletteNumber-1) > 0) showNumberAnim(i,WAIT_TIME_ONE_AND_HALF_SECOND);
            else if(i <= rouletteNumber) showNumberAnim(i,WAIT_TIME_TWO_AND_HALF_SECOND);
        }
    }

    private static void animationCompleteRotation(int rouletteNumber,int animationTimeForFirstNumbers){
        for(int i = 0;i <= 9;++i) {
            if ((i+3) <= (rouletteNumber+10)) showNumberAnim(i, animationTimeForFirstNumbers);
            else if ((i+2) <= (rouletteNumber+10)) showNumberAnim(i, WAIT_TIME_HALF_SECOND);
            else if ((i+1) <= (rouletteNumber+10)) showNumberAnim(i, WAIT_TIME_ONE_AND_HALF_SECOND);
        }
    }

    public static void showNumberAnim(int number,int time){
        showNumber(number);
        Time.sleep(time);
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