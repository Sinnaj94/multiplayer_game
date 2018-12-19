package com.network.client;

import com.game.gameworld.Renderer;
import com.network.common.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Socket socket;
    Manager m;
    Manager m2;
    Scanner s;
    public volatile boolean running;
    public Client(int port) {
        try {
            ClientGameLogic c = new ClientGameLogic();
            Thread cl = new Thread(c);
            cl.start();
            running = true;
            s = new Scanner(System.in);
            socket = new Socket("localhost", port);
            m = new Manager(socket.getInputStream(), socket.getOutputStream());
            m.register(MessageType.GAME_OBJECT, new GameObjectMessageHandler());
            // Receive the ChatMessages
            Thread ta = new Thread(m);
            ta.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientLoop() {
        while (running) {

        }
    }

    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setSize(new Dimension(400, 800));
        splashScreen.setResizable(false);
        splashScreen.pack();
        splashScreen.setVisible(true);

        //
        // Client a = new Client(6060);
    }

    @Override
    public void run() {

    }


}

class SplashScreen extends JFrame {
    public SplashScreen() {
        super();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.add(new TextArea("Willkommen bei Flyff."));
        panel.add(new Button("Connect to FLYFF HEADQUARTER"));
        this.add(panel);
    }

}
