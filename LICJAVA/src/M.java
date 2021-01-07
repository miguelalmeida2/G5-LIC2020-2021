

public class M {

    private static final String[] KEYOPTIONS = {"*-Play  8-ShutD", "0-Stats  #-Count"};
    private static char key = 0;

    public static void main(String[] args) {

    }

    public static void MMenu () {
        char pressed = 0;
        boolean b = false;
        TUI.clearScreen();
        TUI.write("On Maintenance");
        while (true) {

            int i = b ? 1 : 0;
            TUI.write(KEYOPTIONS[i], 1, 0);

            pressed = KBD.waitKey(5000);
            if (pressed == '*' || pressed == '0' || pressed == '#' || pressed == '8') break;
            b = !b;
        }
        key = pressed;
    }

    private static void Shutdown(){ System.exit(0); }





}
