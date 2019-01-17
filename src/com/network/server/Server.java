package com.network.server;

import com.game.event.Event;
import com.game.event.gameobject.AddGameObjectEvent;
import com.game.event.gameobject.GameObjectEvent;
import com.game.gameworld.*;
import com.game.render.Renderer;
import com.network.common.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Server implements Runnable {
    // Accepting the connections
    private ServerSocket serverSocket;
    private Map<Integer, Socket> clients;
    private BlockingQueue<NetworkMessage> messages;

    public Map<String, Manager> getManagers() {
        return managers;
    }

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
        accessor = World.getInstance().getAccessor();
        new Thread(new CleanupThread()).start();
    }

    @Override
    public void run() {
        while (!exit) try {
            // TODO: The manager should be removed when player exits
            Socket socket = serverSocket.accept();
            // Check for the username
            String wishedUsername = new DataInputStream(socket.getInputStream()).readUTF();
            String username = wishedUsername;
            int id = 0;

            while(managers.containsKey(username)) {
                System.out.println("Duplicate name detected. Changing.");
                username = String.format("%s_%d", wishedUsername, id);
                id++;
            }



            if(managers.containsKey(username)) {
                // Throw him from the server.
                //manager.send(new ConfigMessage());
                socket.close();
                return;
            }

            Manager manager = new Manager(socket.getInputStream(), socket.getOutputStream());
            manager.setSocket(socket);
            manager.register(MessageType.CONFIG, new ConfigMessageHandler());

            System.out.println(String.format("User %s has Successfully connected. Welcome %s!", username, username));
            manager.register(MessageType.EVENT, eventMessageHandler);

            // Send all Objects to current user

            // Spawn the player on the map using the Accessor
            Player player = accessor.addPlayer(username);
            // Send the Config Message. => Right Player should be assigned to the client!
            // Command Messages (Steering of the Player)
            commandMessageHandler.setPlayer(player);
            manager.register(MessageType.COMMAND, commandMessageHandler);
            commandMessageHandler.addAccessor(manager.getAccessor());

            // Put the account in the hashmap
            managers.put(username, manager);


            Thread managerThread = new Thread(manager);
            managerThread.start();

            // Synchronize the Game Objects
            new Thread(() -> {
                try {
                    synchronized (ServerGameLogic.tickToken) {
                        ServerGameLogic.tickToken.wait();
                    }
                    for(GameObject g:accessor.get()) {
                        manager.send(new EventMessage(new AddGameObjectEvent(g)));
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(new ServerTickThread(manager)).start();
            manager.send(new ConfigMessage(player.getID()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Thread stopped.");
    }



    public void stop() {
        exit = true;
    }

    public class CleanupThread implements Runnable {
        @Override
        public void run() {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            while(!exit) {
                try {
                    List<String> removed = new ArrayList<>();
                    for(Map.Entry<String, Manager> entry: managers.entrySet()) {
                        if(entry.getValue().inactive) {
                            System.out.println("Cleaning up Manager from User " + entry.getKey());
                            removed.add(entry.getKey());
                            accessor.removePlayerByName(entry.getKey());
                        }
                    }
                    for(String r:removed) {
                        managers.remove(r);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
