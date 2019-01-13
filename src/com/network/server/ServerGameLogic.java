package com.network.server;

import com.game.gameworld.Renderer;
import com.game.gameworld.World;
import com.game.input.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ServerGameLogic implements Runnable {
    List<Command> requestedCommands;
    private Object token;
    public static int UPDATESPERSECOND = 60;
    private static ServerGameLogic instance;
    private World world;
    private Server server;
    private final int UPDATE_RATE = 10;
    private final int CLIENT_UPDATE_RATE = 100;
    private long updateCount;
    private long lastTime;
    private Renderer renderer;
    public volatile boolean exit = false;

    public ServerGameLogic(int port) throws IOException {
        server = new Server(port);
        new Thread(server).start();
        world = World.getInstance();
        updateCount = 0;

        requestedCommands = new ArrayList<>();
    }

    public void setToken(Object token) {
        this.token = token;
    }

    @Override
    public void run() {
        System.out.println("Running thread");
        while (!exit) {
            synchronized (World.getInstance()) {
                if (System.currentTimeMillis() - lastTime > UPDATE_RATE) {
                    update();
                    world.notify();
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server shutting down.");
        server.stop();
    }

    public void stop() {
        this.exit = true;
    }

    public void addCommand(Command c) {
        requestedCommands.add(c);
    }


    /**
     * This is where the calculations happen.
     */
    private void update() {

        world.update();
        updateCount++;
        if (updateCount % CLIENT_UPDATE_RATE == 0) {
            // TODO
            server.deliverToClients();
        }
    }

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            ServerGameLogic serverGameLogic = new ServerGameLogic(port);
        } catch (IOException e) {
            System.out.println("WHAT THE HECK?");
        }
    }
}

