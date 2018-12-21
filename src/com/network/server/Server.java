package com.network.server;

import com.game.event.AddGameObjectEvent;
import com.game.event.MoveGameObjectEvent;
import com.game.gameworld.*;
import com.network.common.*;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.net.BindException;
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
    private EventMessageHandler eventMessageHandler;
    //private List<CommandMessageHandler> commandMessageHandlers;
    private CommandMessageHandler commandMessageHandler;
    private int port;
    private volatile boolean exit = false;
    private static Object token = new Object();
    public Server(int port) throws IOException {
        managers = new ArrayList<>();
        this.port = port;
        serverSocket = new ServerSocket(port);
        //commandMessageHandlers = new ArrayList<>();
        commandMessageHandler = new CommandMessageHandler();
        eventMessageHandler = new EventMessageHandler();
        System.out.println(String.format("Success! Server listening on Port %d!", port));
    }

    public void deliverToClients() {
        for(Manager m:managers) {
            for(PhysicsObject g:World.getInstance().getPlayers().values()) {
                System.out.println("TICK");
                m.send(new EventMessage(new MoveGameObjectEvent(g)));
            }
        }
    }


    @Override
    public void run() {
        while (!exit) {
            try {
                System.out.println("Listening for new Connections.");
                Socket t = serverSocket.accept();
                Manager ma = new Manager(t.getInputStream(), t.getOutputStream());
                ma.register(MessageType.EVENT, eventMessageHandler);
                // Add a new Player and connect it with the current Command Message Handler
                Player p = World.getInstance().spawnPlayer();

                ma.send(new EventMessage(new AddGameObjectEvent(p, true)));
                //CommandMessageHandler commandMessageHandler = new CommandMessageHandler();
                commandMessageHandler.setPlayer(p);
                ma.register(MessageType.COMMAND, commandMessageHandler);
                //commandMessageHandlers.add(commandMessageHandler);
                //for(CommandMessageHandler c:commandMessageHandlers) {
                commandMessageHandler.addOutputStream(ma.getDos());
                //}
                managers.add(ma);
                // Sending this one the right one
                //TODO: put it in the handler
                for(Player player:World.getInstance().getPlayers().values()) {
                    if(player!=p) {
                        ma.send(new EventMessage(new AddGameObjectEvent(player)));
                    }
                }
                for(Manager m:managers) {
                    if(m!=ma) {
                        m.send(new EventMessage(new AddGameObjectEvent(p)));
                    }
                }
                Thread managerThread = new Thread(ma);
                managerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server Thread stopped.");
    }

    public void stop() {
        exit = true;
    }
}
