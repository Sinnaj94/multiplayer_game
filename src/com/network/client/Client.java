package com.network.client;

import com.game.gameworld.GameObject;
import com.game.gameworld.Renderer;
import com.game.input.Command;
import com.network.common.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    Socket socket;
    Manager manager;
    Manager m2;
    public volatile boolean running;
    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        running = true;

        manager = new Manager(socket.getInputStream(), socket.getOutputStream());
        manager.register(MessageType.EVENT, new EventMessageHandler());
        //manager.register(MessageType.MOVE, new MoveMessageHandler());
        manager.register(MessageType.COMMAND, new CommandMessageHandler());
        // Receive the ChatMessages
        Thread ta = new Thread(manager);
        ta.start();
    }

    public void sendCommand(Command c) {
        manager.send(new CommandMessage(c));
    }
}
