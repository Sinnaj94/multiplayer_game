package com.network.server;

import com.game.event.Event;
import com.game.event.player.HitPlayerEvent;
import com.game.event.player.KillPlayerEvent;
import com.game.gameworld.*;
import com.game.gameworld.players.Player;
import com.game.render.Renderer;
import com.game.event.player.Command;
import com.network.common.EventMessage;
import com.network.common.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class ServerGameLogic implements Runnable {
    List<Command> requestedCommands;
    public static final Object tickToken = new Object();
    public static int UPDATESPERSECOND = 60;
    private static ServerGameLogic instance;
    private World world;
    private Server server;
    private final int UPDATE_RATE = 10;
    private final int CLIENT_UPDATE_RATE = 40;
    private long lastTime;
    private Renderer renderer;
    public volatile boolean exit = false;
    private World.Accessor accessor;

    public ServerGameLogic(int port) throws IOException {
        server = new Server(port);
        new Thread(server).start();
        world = World.getInstance();
        requestedCommands = new ArrayList<>();
        accessor = world.getAccessor();
    }

    @Override
    public void run() {
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

    private void syncEventsToManagers() {
        Queue<Event> queue = accessor.getSynchronizedEvents();
        while(!queue.isEmpty()) {
            Event event = queue.poll();
            for(Manager m:server.getManagers().values()) {
                m.send(new EventMessage(event));
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
     * This is where the Server-Site-Calculations happen.
     */
    private void update() {
        // Collisions
        world.update();
        for(Map.Entry<Player, Item> entry : accessor.getPlayerItemCollisions().entrySet()) {
            //entry.getValue().give(entry.getKey());
            if(entry.getValue().canTake(entry.getKey())) {
                entry.getValue().assign(entry.getKey());
            }
        }
        for(Player player: accessor.getAllPlayers()) {
            if(player.getPosition().getY() > World.DEATHZONE) {
                accessor.addEvent(new HitPlayerEvent(player.getID()));
            }
            if((player.isDead()) && !player.isResetRequested()) {
                accessor.addEvent(new KillPlayerEvent(player.getID()));
            }
            if(player.isShoot()) {
                accessor.add(new Bullet(player.getMiddle(), player.getShootDirection(), player.getID()));
                player.shot();
            }
        }
        for(Map.Entry<Bullet, Player> entry: accessor.getBulletPlayerCollisions().entrySet()) {
            if(entry.getKey().getPlayerID() != entry.getValue().getID()) {
                accessor.addEvent(new HitPlayerEvent(entry.getValue().getID()));
                accessor.remove(entry.getKey().getID());
            }
        }
        for(Bullet b : accessor.getBullets()) {
            // Collision with map
            if(b.getCollisionCache().collides()) {
                accessor.remove(b.getID());
            }
        }
        synchronized (tickToken) {
            tickToken.notifyAll();
        }
    }

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            ServerGameLogic serverGameLogic = new ServerGameLogic(port);
            new Thread(serverGameLogic).start();
            Renderer r = new Renderer("server_view");
            r.addMapControl();
            r.addComponent(new AdminPanel(World.getInstance().getAccessor()));
        } catch (IOException e) {
            System.out.println("Connection not possible.");
        }
    }
}

