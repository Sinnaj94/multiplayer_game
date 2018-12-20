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

public class Server implements Runnable {
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
        managers = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            gameObjectMessageHandler = new GameObjectMessageHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new Server(6060);
    }

    public void deliverToClients() {
        for(Manager m:managers) {
            for(PhysicsObject g:World.getInstance().getPlayers().values()) {
                if(g.hasMotionChanges()) {
                    m.send(new GameObjectMessage(g));
                }
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket t = serverSocket.accept();
                Manager ma = new Manager(t.getInputStream(), t.getOutputStream());
                ma.register(MessageType.GAME_OBJECT, gameObjectMessageHandler);
                ma.register(MessageType.COMMAND, new CommandMessageHandler());
                managers.add(ma);
                Thread managerThread = new Thread(ma);
                managerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
