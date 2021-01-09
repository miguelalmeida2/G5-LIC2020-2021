import isel.leic.utils.Time;

public class M {

    private static final String[] KEYOPTIONS = {"0-Stats #-Count ", "*-Play  8-ShutD "};

    public static void maintenanceMenu() {
        char pressed = 0;
        boolean b = false;
        TUI.clearScreen();
        TUI.write(" On Maintenance ");

        while (true) {

            RouletteGameApp.checkIfMaintenanceButtonOff();
            int i = b ? 1 : 0;
            TUI.write(KEYOPTIONS[i], 1, 0);
            pressed = KBD.waitKey(5000);
            if(pressed == '0') {

                //TO DO

            }if(pressed == '#'){

                //TO DO

            }if(pressed == '*') RouletteGameApp.gameRotation(true);
            if(pressed == '8') shutdown();
            b = !b;
        }
    }

    private static void shutdown(){
        TUI.write("    Shutdown    ",0,0);
        TUI.write("5-Yes  other-No ",1,0);
        char key = KBD.waitKey(5000);
        if (key == '5') {
            System.exit(0);

            //save scores and stats here

        }maintenanceMenu();
    }
}
