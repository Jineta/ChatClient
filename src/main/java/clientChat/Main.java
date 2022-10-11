package clientChat;

import gui.LoginFrame;

public class Main {

    public static void main(String[] args) {
        ClientSocket.getClientSocket();
        ClientReaderThread.getClientThreadInstance();
        new LoginFrame();
    }
}
