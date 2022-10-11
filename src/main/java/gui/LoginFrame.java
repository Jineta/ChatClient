package gui;

import clientChat.ClientSocket;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static clientChat.ClientSocket.SERVER_IP;

public class LoginFrame {
    String login;
    JTextField loginTextField;
    JButton loginOkButton;
    JButton loginCancelButton;
    JFrame loginFrame;

    public LoginFrame() {

        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.getContentPane().add(getLoginPanel());
        loginFrame.setLocation(300, 300); //
        loginFrame.pack();
        loginFrame.setVisible(true);

    }

    private JPanel getLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());

        loginPanel.add(getTextPanel(), BorderLayout.CENTER);
        loginPanel.add(getButtonPanel(), BorderLayout.PAGE_END);
        return loginPanel;
    }

    private JPanel getButtonPanel() {
        loginOkButton = new JButton("OK");
        loginOkButton.addActionListener(new LoginOkButtonActonListener());
        loginOkButton.setEnabled(false);

        loginCancelButton = new JButton("Cancel");
        loginCancelButton.addActionListener(new LoginCancelButtonActonListener());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.add(loginOkButton);
        buttonPanel.add(loginCancelButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,5));
        return buttonPanel;
    }

    private JPanel getTextPanel() {
        JLabel loginLabel = new JLabel("Please enter login: ");
        loginLabel.setLabelFor(loginTextField);

        loginTextField = new JTextField();
        loginTextField.setColumns(20);
        loginTextField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        loginTextField.getDocument().addDocumentListener(new LoginTextFieldDocumentListener());

        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.setBackground(Color.GRAY);
        textPanel.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
        textPanel.add(loginLabel);
        textPanel.add(loginTextField);

        return textPanel;
    }

    public String getLogin() {
        return login;
    }

    public class LoginTextFieldDocumentListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            changed();
        }

        public void removeUpdate(DocumentEvent e) {
            changed();
        }

        public void insertUpdate(DocumentEvent e) {
            changed();
        }

        public void changed() {
            if (loginTextField.getText().isBlank()) {
                loginOkButton.setEnabled(false);
            } else {
                loginOkButton.setEnabled(true);
            }

        }
    }

    private class LoginOkButtonActonListener implements ActionListener {
        public static final String BIND_CHECK_LOGIN = "server.checkLogin";

        public void actionPerformed(ActionEvent actionEvent) {
            final Registry registry;

            try {
                login = loginTextField.getText();
                registry = LocateRegistry.getRegistry(SERVER_IP, 4344);
                //получаем объект (прокс-сервер)
                serverLocalChat.CheckLogin service = (serverLocalChat.CheckLogin)registry.lookup("rmi://"+SERVER_IP+":4344/"+BIND_CHECK_LOGIN);
                //Вызываем удаленный метод
                boolean result = service.checkLogin(login);
            }
            catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
            loginFrame.dispose();
            new ChatFrame(login);
            ClientSocket.sendMessage(login + " joined to chat");
        }
    }

    private class LoginCancelButtonActonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }


    }
}

