package network.server;

import game.ServerGameLogic;
import game.gameworld.Renderer;
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
    private ServerGameLogic serverGameLogic;
    private Renderer renderer;
    private static Object token = new Object();
    public Server(int port) {
        try {
            serverGameLogic = new ServerGameLogic();
            Thread logicThread = new Thread(serverGameLogic);
            // TODO: Take out the renderer.
            renderer = new Renderer();
            Thread renderThread = new Thread(renderer);
            logicThread.start();
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
                m.registerPlayer(serverGameLogic.addPlayer());
                m.registerServerGameLogic(serverGameLogic);
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
