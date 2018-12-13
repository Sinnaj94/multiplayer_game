package com.network.server;

import com.game.gameworld.GameObject;
import com.game.gameworld.PhysicsObject;
import com.game.gameworld.Renderer;
import com.game.gameworld.World;
import com.network.common.*;

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

    public static void main(String[] args) {
        Server s = new Server(6060);
    }

    public void deliverToClients() {
        for(Manager m:managers) {
            for(GameObject g:World.getInstance().getPlayers()) {
                m.send(new GameObjectMessage(g));
            }
        }

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
                for(PhysicsObject g: World.getInstance().getPlayers()) {
                    System.out.println(g);
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
