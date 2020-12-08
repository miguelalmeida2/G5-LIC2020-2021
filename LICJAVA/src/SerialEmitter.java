import isel.leic.UsbPort;
import isel.leic.utils.*;

public class SerialEmitter { // Envia tramas para o módulo Serial Receiver.
    public static enum Destination {RDisplay,LCD};

    // Inicia a classe
    public static void init() {}

    // Envia uma trama para o Serial Receiver identificando o destino em addr e os bits de dados em ‘data’.
    public static void send(Destination addr, int data) {}
}