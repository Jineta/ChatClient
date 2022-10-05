package gui;

import clientChat.ClientSocket;
import clientChat.ClientReaderThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatFrame {
    String login;
    JTextArea sendMessageTextArea;
    JFrame mainFrame;
    /* Создание окна с рамкой и заголовком*/
    public ChatFrame(String login) {
        this.login = login;
//main frame
        mainFrame = new JFrame("Cyber chat");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(300, 500));
//mainPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
//buttons
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitListener());

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendListener());

        //sendMessage area (bottom)
        sendMessageTextArea = new JTextArea();
        sendMessageTextArea.setLineWrap(true);
        JScrollPane sendMessageScroll = new JScrollPane(sendMessageTextArea);
        sendMessageScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sendMessageScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


//received message area
        JScrollPane incomingListScroll = new JScrollPane(ClientReaderThread.getClientThreadInstance().getIncomingMessagesList());

        Box textBox = new Box(BoxLayout.Y_AXIS);
        JToolBar toolBar = new JToolBar(1);


        textBox.add(incomingListScroll);
        textBox.add(sendMessageScroll);
        toolBar.add(exitButton);
        toolBar.add(sendButton);
        toolBar.setMaximumSize(mainFrame.getMaximumSize());


        mainPanel.add(textBox);
        mainPanel.add(toolBar);//
        mainPanel.add(Box.createHorizontalGlue());
        mainFrame.getContentPane().add(mainPanel).setBackground(Color.GRAY);
        mainFrame.setBounds(100, 100, 300, 500);
        //mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private class SendListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            ClientSocket.sendMessage(login+ ": " + sendMessageTextArea.getText());
            sendMessageTextArea.setText("");
        }
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            mainFrame.dispose();
            ClientSocket.sendMessage(login+ " left the chat");
            System.exit(0);

        }
    }
}
