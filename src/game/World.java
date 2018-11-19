package game;

import game.generics.Renderable;
import helper.Vector2I;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class World implements Runnable {
    private List<Player> players;
    private Vector2I size;

    private Level level;
    private Stack<Renderable> renderables;

    /**
     * @param size The number of tiles in the world...
     */
    public World(Vector2I size) {
        players = new ArrayList<>();
        renderables = new Stack<>();
        // TODO: Players.add should be made by
        this.size = size;
        level = new Level("test.png");
        buildWorld();
    }

    private void buildWorld() {
        level.buildLevel();
    }

    @Override
    public void run() {
        while (true) {
            update();
        }
    }

    /**
     * TODO: Calculates the new State of the world...
     */
    private void update() {
        for (Player p : players) {
            //p.move();
        }
    }

    public Player addPlayer() {
        Player t = new Player();
        players.add(t);
        // T: Referenz
        return t;
    }

    public Vector2I getSize() {
        return size;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
