package com.network.server;

import com.game.ai.PlayerAI;
import com.game.event.Event;
import com.game.gameworld.Item;
import com.game.gameworld.Player;
import com.game.render.Renderer;
import com.game.gameworld.World;
import com.game.event.player.Command;
import com.helper.Vector2f;
import com.network.common.EventMessage;
import com.network.common.Manager;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class ServerGameLogic implements Runnable {
    List<Command> requestedCommands;
    private Object token;
    public static int UPDATESPERSECOND = 60;
    private static ServerGameLogic instance;
    private World world;
    private Server server;
    private final int UPDATE_RATE = 10;
    private final int CLIENT_UPDATE_RATE = 40;
    private long updateCount;
    private long lastTime;
    private Renderer renderer;
    public volatile boolean exit = false;
    private World.Accessor accessor;

    public ServerGameLogic(int port) throws IOException {
        server = new Server(port);
        new Thread(server).start();
        world = World.getInstance();
        updateCount = 0;
        requestedCommands = new ArrayList<>();
        accessor = world.getAccessor();
        accessor.add(new Item(new Vector2f(200f, -200f)));
        new PlayerAI(accessor.add(new Player("AI")).getID(), accessor);
    }

    @Override
    public void run() {
        System.out.println("Running thread");
        while (!exit) {
            synchronized (World.getInstance()) {
                if (System.currentTimeMillis() - lastTime > UPDATE_RATE) {
                    update();
                    syncEventsToManagers();
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

    // FIXME sync events to all managers => ConcurrentModificationException
    private void syncEventsToManagers() {
        BlockingQueue<Event> queue = accessor.getSynchronizedEvents();
        while(!queue.isEmpty()) {
            try {
                Event event = queue.take();
                for(Manager m:server.getManagers().values()) {
                    m.send(new EventMessage(event));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        // Collisions
        for(Map.Entry<Player, Item> entry : accessor.getPlayerItemCollisions().entrySet()) {
            entry.getValue().give(entry.getKey());
        }
        updateCount++;
    }

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            ServerGameLogic serverGameLogic = new ServerGameLogic(port);
            new Thread(serverGameLogic).start();
            Renderer r = new Renderer("server_view");
            new Thread(r).start();
            new AdminPanel(World.getInstance().getAccessor());
        } catch (IOException e) {
            System.out.println("Connection not possible.");
        }
    }
}

