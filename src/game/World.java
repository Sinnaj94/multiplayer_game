package game;

import game.Input.Command;
import game.gameobjects.Player;
import game.generics.Renderable;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class World implements Runnable {
    private List<Player> players;
    private Vector2i size;



    private Level level;
    private Stack<Renderable> renderables;

    List<Command> requestedCommands;

    int commandCount;
    /**
     * @param size The number of tiles in the world...
     */
    public World(Vector2i size) {
        players = new ArrayList<>();
        renderables = new Stack<>();
        // TODO: Players.add should be made by
        requestedCommands = new ArrayList<>();
        this.size = size;
        level = new Level("test.png", "res/tilesets/forest_tiles.json");
        buildWorld();
        commandCount = 0;
    }

    private void buildWorld() {
        level.buildLevel();
    }

    @Override
    public void run() {
        while (true) {
            update();
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addCommand(Command c) {
        System.out.println(++commandCount);
        requestedCommands.add(c);
    }


    /**
     * This is where the calculations happen.
     */
    private void update() {
        for(Player p:players) {
            for(Command c:requestedCommands) {
                c.execute();
            }
            requestedCommands.clear();
            p.update();
        }
    }

    public Player addPlayer() {
        Player t = new Player();
        players.add(t);
        // T: Referenz
        return t;
    }

    public Level getLevel() {
        return level;
    }

    public Vector2i getSize() {
        return size;
    }


    public List<Player> getPlayers() {
        return players;
    }
}
