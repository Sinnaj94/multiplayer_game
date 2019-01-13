package com.network.client;

import com.game.input.Command;
import com.network.common.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    Socket socket;
    Manager manager;
    Manager m2;
    public volatile boolean running;

    public Client(String ip, int port, String name) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 1000);
        DataOutputStream d = new DataOutputStream(socket.getOutputStream());
        d.writeUTF(name);
        running = true;

        manager = new Manager(socket.getInputStream(), socket.getOutputStream());
        manager.register(MessageType.CONFIG, new ConfigMessageHandler());
        manager.register(MessageType.EVENT, new EventMessageHandler());
        //manager.register(MessageType.MOVE, new MoveMessageHandler());
        manager.register(MessageType.COMMAND, new CommandMessageHandler());
        // Thread for the Manager start
        Thread ta = new Thread(manager);
        ta.start();
    }

    public void sendCommand(Command c) {
        manager.send(new CommandMessage(c));
    }
}

