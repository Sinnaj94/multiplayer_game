package network.server;

import game.gameworld.GameObject;
import game.gameworld.Renderer;
import game.gameworld.World;
import network.common.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private GameObjectMessageHandler gameObjectMessageHandler;
    private static Object token = new Object();
    public Server(int port) {
        try {
            serverGameLogic = new ServerGameLogic(this);
            Thread logicThread = new Thread(serverGameLogic);
            // TODO: Take out the renderer... TO A FANCY RESTAURANT!
            renderer = new Renderer();
            Thread renderThread = new Thread(renderer);
            logicThread.start();
            renderThread.start();
            serverSocket = new ServerSocket(port);
            // ChatMessageHandler
            gameObjectMessageHandler = new GameObjectMessageHandler();
            serverLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGameObject(GameObject g) {
        if(managers!=null) {
            for(Manager m:managers) {
                m.send(new GameObjectMessage(g));
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server(6060);
    }

    private void serverLoop() {
        managers = new ArrayList<>();
        //MoveMessageHandler m = new MoveMessageHandler();
        while (true) {
            try {
                Socket t = serverSocket.accept();
                Manager ma = new Manager(t.getInputStream(), t.getOutputStream());
                ma.register(MessageType.GAME_OBJECT, gameObjectMessageHandler);
                managers.add(ma);
                Thread managerThread = new Thread(ma);
                managerThread.start();
                for(GameObject g: World.getInstance().getPlayers()) {
                    GameObjectMessage ga = new GameObjectMessage(g);
                    ma.send(ga);
                }

                // Register the existing ChatMessageHandler and MoveManager
                /*MoveMessageHandler m = new MoveMessageHandler();
                m.registerPlayer(serverGameLogic.addPlayer());
                m.registerServerGameLogic(serverGameLogic);
                ma.register(MessageType.CHAT, c);
                ma.register(MessageType.MOVE, m);*/
                //managers.add(ma);
                //ma.register(MessageType);
                //managers.add(MessageType.GAME_OBJECT, gameObjectMessageHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
