package game;

import game.gameobjects.Player;
import game.generics.Renderable;
import helper.Vector2f;
import helper.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class World implements Runnable {
    private List<Player> players;
    private Vector2i size;



    private Level level;
    private Stack<Renderable> renderables;

    /**
     * @param size The number of tiles in the world...
     */
    public World(Vector2i size) {
        players = new ArrayList<>();
        renderables = new Stack<>();
        // TODO: Players.add should be made by
        this.size = size;
        level = new Level("test.png", "res/tilesets/forest_tiles.json");
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
     * This is where the calculations happen.
     */
    private void update() {
        for(Player p:getPlayers()) {
            p.move(new Vector2f(0.00001f, 0f));
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
