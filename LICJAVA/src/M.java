public class M {

    public static char maintenanceMenu() {
        RouletteGameApp.checkIfMaintenanceButtonOff();
        char pressed = '?';w
        boolean b = false;
        int c = 1;
        TUI.clearScreen();
        TUI.write(" On Maintenance ");
        while (pressed != '0' & pressed != '#' & pressed != '*' & pressed != '8'){
            RouletteGameApp.checkIfMaintenanceButtonOff();
            int i = b ? 1 : 0;
            TUI.write(RouletteGameApp.KEYOPTIONS[i], 1, 0);
            pressed = KBD.waitKey(RouletteGameApp.WAIT_TIME_5SEC);
            b = !b;
        }return pressed;
    }

}
