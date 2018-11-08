package network.server;

import network.common.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Server {
    // Accepting the connections
    private ServerSocket serverSocket;
    private Map<Integer, Socket> clients;
    private BlockingQueue<NetworkMessage> messages;
    private List<Manager> managers;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            // ChatMessageHandler
            serverLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serverLoop() {
        ChatMessageHandler c = new ChatMessageHandler();
        MoveMessageHandler m = new MoveMessageHandler();
        while(true) {
            try {
                Socket t = serverSocket.accept();
                Manager ma = new Manager(t.getInputStream(), t.getOutputStream());
                c.addOutputStream(t.getOutputStream());
                // Register the existing ChatMessageHandler and MoveManager
                ma.register(MessageType.CHAT, c);
                ma.register(MessageType.MOVE, m);
                //managers.add(ma);
                Thread managerThread = new Thread(ma);
                managerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server(6060);
    }
}
