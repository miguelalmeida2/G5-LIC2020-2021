import isel.leic.utils.Time;

public class M {

    private static final String[] KEYOPTIONS = {"*-Play  8-ShutD", "0-Stats #-Count "};
    private static char key = 0;

    public static void main(String[] args) {

    }

    public static void maintainanceMenu () {
        char pressed = 0;
        boolean b = false;

        while (true) {
            //RouletteGameApp.checkIfMaintainanceButtonOff();
            TUI.clearScreen();
            TUI.write(" On Maintenance ");


            int i = b ? 1 : 0;
            TUI.write(KEYOPTIONS[i], 1, 0);

            pressed = KBD.waitKey(5000);
            if(pressed == '0') {

                //TO DO
                break;

            }if(pressed == '#'){

                //TO DO
                break;

            }if(pressed == '*') RouletteGameApp.gameRotation(true);
            if(pressed == '8') shutdown();

            b = !b;
            key = pressed;
        }
    }

    private static void shutdown(){

        //falta implementar timeout 5s

        int firstTime = (int)Time.getTimeInMillis();
        int currentTime;
        TUI.write("    Shutdown    ",0,0);
        TUI.write("5-Yes  other-No ",1,0);

        char key = RouletteGameApp.readKey();
        if(key == '5') {
            System.exit(0);

            //save scores and stats here

        }RouletteGameApp.gameRotation(false);
    }
}
