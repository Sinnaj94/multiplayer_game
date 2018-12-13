package com.network.server;

import com.game.gameworld.PhysicsObject;
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
    private final long UPDATE_RATE = 20;
    private long lastTime;
    public ServerGameLogic(Server server) {
        this.server = server;
        world = World.getInstance();
        world.addTestScene();
        requestedCommands = new ArrayList<>();
    }

    public void setToken(Object token) {
        this.token = token;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (World.getInstance()) {
                if(System.currentTimeMillis() - lastTime > UPDATE_RATE) {
                    update();
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
        for(PhysicsObject gameObject:world.getDynamics()) {
            gameObject.update();
        }
        world.removeObjects();
        world.getLevel().update();
    }

    public Player addPlayer() {
        // TODO: Referenzen speichern.
        Player t = new Player(new Vector2f(0f, 0f));
        world.addObject(t);
        return t;
    }
}
