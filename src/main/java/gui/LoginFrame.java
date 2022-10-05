package gui;

import clientChat.ClientSocket;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        public void actionPerformed(ActionEvent actionEvent) {
            login = loginTextField.getText();
            //TODO add validation for duplicates
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

