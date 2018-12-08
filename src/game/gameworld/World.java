package game.gameworld;

import game.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World {
    private List<GameObject> gameObjects;



    private Level level;

    private static World instance;

    /**
     * Singleton implementation, there can only be ONE world object.
     */
    public static synchronized World getInstance() {
        if(World.instance == null) {
            World.instance = new World();
        }
        return World.instance;
    }


    public void addTestScene() {
        Player p = new Player();
        gameObjects.add(p);
    }

    public World() {
        gameObjects = new ArrayList<>();
        level = new Level("test.png", "res/tilesets/forest_tiles.json");
    }

    private void build() {
        level.build();
    }

    /**
     * Adds a Gameobject
     * @return The added Gameobject, so it will work further
     */
    public void addObject(GameObject g) {
        gameObjects.add(g);
    }

    /**
     * Returns the current level
     * @return
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Get all the GameObjects
     * @return
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

}
