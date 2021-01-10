public class M {

    private static final int WAIT_TIME_5SEC = 5000; //5seg

    private static final String[] KEYOPTIONS = {"0-Stats #-Count ", "*-Play  8-ShutD "};

    public static void maintenanceMenu() {
        //RouletteGameApp.MAINTENANCE_COINS=100;
        RouletteGameApp.checkIfMaintenanceButtonOff();

        char pressed;
        boolean b = false;
        TUI.clearScreen();
        TUI.write(" On Maintenance ");

        while (true) {
            RouletteGameApp.checkIfMaintenanceButtonOff();
            int i = b ? 1 : 0;
            TUI.write(KEYOPTIONS[i], 1, 0);
            pressed = KBD.waitKey(WAIT_TIME_5SEC);
            if(pressed == '0') {

                //TO DO

            }if(pressed == '#'){

                //TO DO

            }if(pressed == '*') RouletteGameApp.gameRotation(true);
            if(pressed == '8') shutdownMenu();
            b = !b;
        }
    }

    private static void shutdownMenu(){
        TUI.write("    Shutdown    ",0,0);
        TUI.write("5-Yes  other-No ",1,0);
        char key = KBD.waitKey(WAIT_TIME_5SEC);
        if (key == '5') {
            System.exit(0);

            //save scores and stats here

        }maintenanceMenu();
    }
}
