package clientChat;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;


public class ClientSocket {


    public static final String SERVER_IP = "192.168.1.102";
    public static final int SERVER_PORT = 4343;

    private Socket sock;

    private static ClientSocket clientSocket;

    private ClientSocket() {
        try {
            this.sock = new Socket(SERVER_IP, SERVER_PORT);

        } catch (Exception ex) {
            System.out.println("Couldn't connect, enjoy the loneliness.");
        }
    }

    public static synchronized ClientSocket getClientSocket() {
        if (clientSocket == null) {
            clientSocket = new ClientSocket();
        }
        return clientSocket;
    }

    public static synchronized PrintWriter getOut() {

        PrintWriter out;
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ClientSocket.getClientSocket().sock.getOutputStream())));
            return out;
        } catch (Exception ex) {
            System.out.println("Couldn't get OUT.");
            return null;
        }
    }

    public static synchronized BufferedReader getIn() {
        Socket sock = ClientSocket.getClientSocket().sock;
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(ClientSocket.getClientSocket().sock.getInputStream()));
            return in;
        } catch (Exception ex) {
            System.out.println("Couldn't connect get IN");
            return null;
        }
    }

    public static void sendMessage(String sendMessage) {
        try {
            PrintWriter out = getOut();
            out.println(sendMessage);
            out.flush();
        } catch (Exception ex) {
            System.out.println("Uuuups... Couldn't send it to server.");
        }

    }

}
