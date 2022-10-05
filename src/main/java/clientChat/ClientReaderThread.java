package clientChat;

import javax.swing.*;
import java.util.Vector;

public class ClientReaderThread {
    private Vector<String> incomingMessagesVector = new Vector<String>();
    private JList incomingMessagesList = new JList();


    /**
     * Singelton
     **/
    private static ClientReaderThread clientReaderThreadInstance;

    public static synchronized ClientReaderThread getClientThreadInstance() {
        if (clientReaderThreadInstance == null) {
            clientReaderThreadInstance = new ClientReaderThread();
        }
        return clientReaderThreadInstance;
    }

    private ClientReaderThread() {
        Thread remoteReader = new Thread(new RemoteReader());
        remoteReader.start();
    }


    /****/
    private class RemoteReader implements Runnable {
        @Override
        public void run() {
            String receivedMessage;

            try {
                while ((receivedMessage = ClientSocket.getIn().readLine()) != null) {
                    System.out.println("got an object from server");
                    System.out.println(receivedMessage);
                    incomingMessagesVector.add(receivedMessage);
                    incomingMessagesList.setListData(incomingMessagesVector);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public  JList getIncomingMessagesList() {
        return incomingMessagesList;
    }
}