package network.client;

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
            m.send(c);
        }
    }

    public static void main(String[] args) {
        Client c = new Client(6060);
    }
}
