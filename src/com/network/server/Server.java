package com.network.server;

import com.game.event.AddGameObjectEvent;
import com.game.event.MoveGameObjectEvent;
import com.game.gameworld.*;
import com.helper.BoundingBox;
import com.helper.Vector2f;
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

    private World.Accessor accessor;

    public Server(int port) throws IOException {
        managers = new HashMap<>();
        this.port = port;
        serverSocket = new ServerSocket(port);
        //commandMessageHandlers = new ArrayList<>();
        commandMessageHandler = new CommandMessageHandler();
        eventMessageHandler = new EventMessageHandler();
        System.out.println(String.format("Success! Server listening on Port %d!", port));
        new Thread(WorkingThread.getInstance()).start();
        accessor = World.getInstance().getAccessor();
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
        GameObject i = accessor.add(new Item(new Vector2f(100f, 0f)));
        accessor.remove(i.getID());
        while (!exit) try {
            // TODO: The manager should be removed when player exits
            Socket socket = serverSocket.accept();
            // Check for the username
            String username = new DataInputStream(socket.getInputStream()).readUTF();

            Manager manager = new Manager(socket.getInputStream(), socket.getOutputStream());
            manager.register(MessageType.CONFIG, new ConfigMessageHandler());
            if(managers.containsKey(username)) {
                // Throw him from the server.
                System.out.println("Duplicate name.");
                manager.send(new ConfigMessage());
                socket.close();
                return;
            }

            System.out.println(String.format("User %s has Successfully connected. Welcome %s!", username, username));


            // Spawn the player on the map
            Player player = World.getInstance().spawnPlayer(username);

            // Send the Config Message. => Right Player should be assigned to the client!
            manager.send(new ConfigMessage(player.getID()));
            manager.register(MessageType.EVENT, eventMessageHandler);



            //manager.send(new EventMessage(new AddGameObjectEvent(player, true)));
            // Give ALL THE OTHER ONES this Player (but not this one)

            // Set all the Commands to this player Object
            commandMessageHandler.setPlayer(player);
            manager.register(MessageType.COMMAND, commandMessageHandler);
            commandMessageHandler.addOutputStream(manager.getDos());

            managers.put(username, manager);
            // Give THIS Client all the current GameObjects
            synchronizeManager(manager);

            addPlayerToAll(manager, player);

            Thread managerThread = new Thread(manager);
            managerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Thread stopped.");
    }

    private void synchronizeManager(Manager manager) {
        for (GameObject g : accessor.get()) {
            {
                manager.send(new EventMessage(new AddGameObjectEvent(g)));
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
