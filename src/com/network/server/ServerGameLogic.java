package com.network.server;

import com.game.gameworld.PhysicsObject;
import com.game.gameworld.Renderer;
import com.game.input.Command;
import com.game.gameworld.Player;
import com.game.gameworld.World;
import com.helper.Vector2f;

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
    private final int CLIENT_UPDATE_RATE = 10;
    private long updateCount;
    private long lastTime;
    private Renderer renderer;
    public ServerGameLogic() {
        server = new Server(6060);
        new Thread(server).start();
        world = World.getInstance();
        world.addTestScene();
        renderer = new Renderer();
        new Thread(renderer).start();
        updateCount = 0;

        requestedCommands = new ArrayList<>();
    }

    public void setToken(Object token) {
        this.token = token;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        while (true) {
            synchronized (World.getInstance()) {
                if(System.currentTimeMillis() - lastTime > UPDATE_RATE) {
                    update();
                    updateCount++;
                    if(updateCount % CLIENT_UPDATE_RATE == 0) {
                        // TODO
                        server.deliverToClients();
                    }
                    world.notifyAll();
                    lastTime = System.currentTimeMillis();
                } else {

                }
            }
        }
    }

    public void addCommand(Command c) {
        requestedCommands.add(c);
    }


    /**
     * This is where the calculations happen.
     */
    private void update() {
        // Execute the Commands
        for(Command c:requestedCommands) {
            System.out.println("Executing");
            c.execute();
        }
        requestedCommands.clear();
        // Update the GameObjects
        // TODO: An dieser Stelle vielleicht auch übertragen (GameObjects changes)
        world.update();
    }

    public Player addPlayer() {
        // TODO: Referenzen speichern.
        Player t = new Player(new Vector2f(0f, 0f));
        world.addObject(t);
        return t;
    }

    public static void main(String[] args) {
        ServerGameLogic serverGameLogic = new ServerGameLogic();
        new Thread(serverGameLogic).start();
    }
}
