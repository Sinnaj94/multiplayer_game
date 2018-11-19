package network.server;

import game.Renderer;
import game.World;
import helper.Vector2I;
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
    private World w;
    private Renderer r;

    public Server(int port) {
        try {
            w = new World(new Vector2I(100, 100));
            r = new Renderer(w);
            Thread renderThread = new Thread(r);
            renderThread.start();
            serverSocket = new ServerSocket(port);
            // ChatMessageHandler
            serverLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new Server(6060);
    }

    private void serverLoop() {
        ChatMessageHandler c = new ChatMessageHandler();
        //MoveMessageHandler m = new MoveMessageHandler();
        while (true) {
            try {
                Socket t = serverSocket.accept();
                Manager ma = new Manager(t.getInputStream(), t.getOutputStream());
                c.addOutputStream(t.getOutputStream());
                // Register the existing ChatMessageHandler and MoveManager
                MoveMessageHandler m = new MoveMessageHandler();
                m.registerPlayer(w.addPlayer());
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
}
