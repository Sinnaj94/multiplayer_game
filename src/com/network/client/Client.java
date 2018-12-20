package com.network.client;

import com.game.gameworld.GameObject;
import com.game.gameworld.Renderer;
import com.game.input.Command;
import com.network.common.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class Client {
    Socket socket;
    Manager manager;
    Manager m2;
    public volatile boolean running;
    public Client(int port) {
        try {
            running = true;
            socket = new Socket("localhost", port);
            manager = new Manager(socket.getInputStream(), socket.getOutputStream());
            manager.register(MessageType.GAME_OBJECT, new GameObjectMessageHandler());
            //manager.register(MessageType.MOVE, new MoveMessageHandler());
            manager.register(MessageType.COMMAND, new CommandMessageHandler());
            // Receive the ChatMessages
            Thread ta = new Thread(manager);
            ta.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(Command c) {
        manager.send(new CommandMessage(c));
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
