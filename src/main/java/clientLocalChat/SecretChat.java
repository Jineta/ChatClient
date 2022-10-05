package clientLocalChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Vector;


public class SecretChat {
    String userName;
    PrintWriter out;
    BufferedReader in;
    JTextArea sendMessageTextArea;
    JList incomingMessagesList;
    Vector<String> incomingMessagesVector = new Vector<String>();
//TODO here mainf
    public static void mainf(String[] args) {
        new SecretChat().startUp(args[0]);

    }

    public void startUp(String userName) {
        this.userName = userName;
//настройка сети, ввода/вывода, создание и запуск потока
        //открываем соединение с сервером
        try {
            //Socket sock = new Socket("93.74.104.172", 4343); // внешний адрес
            Socket sock = new Socket("192.168.1.170", 4343); //  IPv4 Address. . . . . . . . . . . : 192.168.1.170(Preferred), предварительно в проводние разрешена сеть
            //Socket sock = new Socket("localhost", 4343);

            //OutputStreamWriter - это подкласс Writer, это мост, который позволяет преобразовать byte stream в character stream
            //BufferedWriter записывает текст в поток вывода символов, буферизуя записанные символы, чтобы обеспечить эффективную запись символов, массивов и строк. Можно указать в конструкторе вторым параметром размер буфера.
            //буфер работает как обертка и ускоряет работу приложения. Буфер будет записывать данные в себя, а потом большим куском файлы на диск.
            //PrintWriter - это подкласс Writer, который используется для печати форматированных данных в OutputStream или другой Writer, которым он управляет.
            //При необходимости PrintWriter может выполнять автоматическую очистку (flush), это означает, что метод flush() будет вызван сразу после вызова метода println(..)  или при печати текста, содержащего символ '\n'.
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            Thread remoteReader = new Thread(new RemoteReader());
            remoteReader.start();
        } catch (Exception ex) {
            System.out.println("Couldn't connect, enjoy the loneliness.");
        }
        buildGUI();
    }

    public void buildGUI() {
        // Создание окна с рамкой и заголовком
        JFrame mainFrame = new JFrame("Cyber chat");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
mainFrame.setMinimumSize(new Dimension(300,500));
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));


        Box textBox = new Box(BoxLayout.Y_AXIS);
        JToolBar toolBar = new JToolBar(1);
ButtonGroup buttonGroup = new ButtonGroup();

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendListener());


        sendMessageTextArea = new JTextArea();
        sendMessageTextArea.setLineWrap(true);
        JScrollPane sendMessageScroll = new JScrollPane(sendMessageTextArea);
        sendMessageScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sendMessageScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


        incomingMessagesList = new JList();
        JScrollPane incomingListScroll = new JScrollPane(incomingMessagesList); //!!!


        Box sendBox = new Box(BoxLayout.X_AXIS);
        sendBox.add(sendMessageScroll);
        sendBox.add(sendButton);

        textBox.add(incomingListScroll);
        textBox.add(sendBox);
        //buttonGroup.add(sendButton);
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

    public class SendListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                //TODO add date and time
                out.println(userName + ": " + sendMessageTextArea.getText());
                out.flush();
            } catch (Exception ex) {
                System.out.println("Uuuups... Couldn't send it to server.");
            }
            sendMessageTextArea.setText("");
        }
    }

    public class RemoteReader implements Runnable {
        @Override
        public void run() {
            String receivedMessage;
            try {
                while ((receivedMessage = in.readLine()) != null) {
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
}

