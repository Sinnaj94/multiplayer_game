package com.network.server;

import com.game.event.AddGameObjectEvent;
import com.game.event.MoveGameObjectEvent;
import com.game.gameworld.PhysicsObject;
import com.game.gameworld.Player;
import com.game.gameworld.Renderer;
import com.game.gameworld.World;
import com.network.common.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Server implements Runnable {
    // Accepting the connections
    private ServerSocket serverSocket;
    private Map<Integer, Socket> clients;
    private BlockingQueue<NetworkMessage> messages;
    private Map<String, Manager> managers;
    private ServerGameLogic serverGameLogic;
    private Renderer renderer;
    private EventMessageHandler eventMessageHandler;
    //private List<CommandMessageHandler> commandMessageHandlers;
    private CommandMessageHandler commandMessageHandler;
    private int port;
    private volatile boolean exit = false;
    private static Object token = new Object();

    public Server(int port) throws IOException {
        managers = new HashMap<>();
        this.port = port;
        serverSocket = new ServerSocket(port);
        //commandMessageHandlers = new ArrayList<>();
        commandMessageHandler = new CommandMessageHandler();
        eventMessageHandler = new EventMessageHandler();
        System.out.println(String.format("Success! Server listening on Port %d!", port));
        new Thread(WorkingThread.getInstance()).start();
    }

    public void deliverToClients() {
        for (Manager current : managers.values()) {
            for (PhysicsObject g : World.getInstance().getPlayers().values()) {
                current.send(new EventMessage(new MoveGameObjectEvent(g)));
            }
        }
    }


    @Override
    public void run() {
        while (!exit) try {
            // TODO: The manager should be removed when player exits
            Socket socket = serverSocket.accept();
            // Check for the username
            String username = new DataInputStream(socket.getInputStream()).readUTF();
            Manager manager = new Manager(socket.getInputStream(), socket.getOutputStream());
            manager.register(MessageType.EVENT, eventMessageHandler);
            // Give this Client all the current GameObjects
            synchronizeManager(manager);
            Player player = World.getInstance().spawnPlayer(username);
            manager.send(new EventMessage(new AddGameObjectEvent(player, true)));
            // Give all the other ones this Player (but not this one)
            addPlayerToAll(manager, player);
            //CommandMessageHandler commandMessageHandler = new CommandMessageHandler();
            commandMessageHandler.setPlayer(player);
            manager.register(MessageType.COMMAND, commandMessageHandler);
            //commandMessageHandlers.add(commandMessageHandler);
            //for(CommandMessageHandler c:commandMessageHandlers) {
            commandMessageHandler.addOutputStream(manager.getDos());
            //}
            managers.put(username, manager);
            // Sending this one the right one
            //TODO: put it in the handler
            addPlayerToAll(manager, player);
            Thread managerThread = new Thread(manager);
            System.out.println(String.format("User %s has connected. Welcome %s!", username, username));
            managerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Thread stopped.");
    }

    private void synchronizeManager(Manager manager) {
        for (Player player : World.getInstance().getPlayers().values()) {
            {
                manager.send(new EventMessage(new AddGameObjectEvent(player)));
            }
        }
    }

    private void addPlayerToAll(Manager manager, Player player) {
        // Synchronize all other Managers with the current Player object
        for (Manager current : managers.values()) {
            if (current != manager) {
                current.send(new EventMessage(new AddGameObjectEvent(player)));
            }
        }
    }

    public void stop() {
        exit = true;
    }
}
