package clientLocalChat;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;


public class SecretChat {
    String userName;
    PrintWriter out;
    BufferedReader in;
    public static void main(String[] args) {
        /*new SecretChat().startUp(args[0]);*/
        new SecretChat().startUp("temp");
    }
    public void startUp(String userName) {
        /*this.userName = userName;
//настройка сети, ввода/вывода, создание и запуск потока
        //открываем соединение с сервером
        try {
            Socket sock = new Socket("93.74.104.172", 4343);
            //OutputStreamWriter - это подкласс Writer, это мост, который позволяет преобразовать byte stream в character stream
            //BufferedWriter записывает текст в поток вывода символов, буферизуя записанные символы, чтобы обеспечить эффективную запись символов, массивов и строк. Можно указать в конструкторе вторым параметром размер буфера.
            //буфер работает как обертка и ускоряет работу приложения. Буфер будет записывать данные в себя, а потом большим куском файлы на диск.
            //PrintWriter - это подкласс Writer, который используется для печати форматированных данных в OutputStream или другой Writer, которым он управляет.
            //При необходимости PrintWriter может выполнять автоматическую очистку (flush), это означает, что метод flush() будет вызван сразу после вызова метода println(..)  или при печати текста, содержащего символ '\n'.
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            Thread remote = new Thread(new RemoteReader());
            remote.start();
        } catch (Exception ex) {
            System.out.println("Couldn't connect, enjoy the loneliness.");
        } */
        buildGUI();
    }
    public void buildGUI() {
        // Создание окна с рамкой и заголовком
        JFrame mainFrame = new JFrame("Cyber chat");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        mainFrame.getContentPane().add(backgroundPanel).setBackground(Color.GRAY );
        mainFrame.setBounds(50, 50, 300, 300);
        mainFrame.pack();
        mainFrame.setVisible(true);

JButton send = new JButton("Send");

    }
    }

