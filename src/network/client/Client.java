package network.client;

import helper.Vector2;
import network.common.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    Manager m;
    Manager m2;
    Scanner s;
    public Client(int port) {
        try {
            s = new Scanner(System.in);
            socket = new Socket("localhost", port);
            m = new Manager(socket.getInputStream(), socket.getOutputStream());
            m.register(MessageType.CHAT, new ChatMessageHandler());
            m.register(MessageType.MOVE, new MoveMessageHandler());
            // Receive the ChatMessages
            Thread t = new Thread(m);
            t.start();

            clientLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientLoop() {
        while(true) {
            String msg = s.nextLine();
            NetworkMessage c = new ChatMessage(msg);
            MoveMessage _m = new MoveMessage(new Vector2(1,1));
            m.send(c);
            m.send(_m);
        }
    }

    public static void main(String[] args) {
        Client c = new Client(6060);
    }
}
