package game.gameworld;

import game.Level;
import helper.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World {
    private List<GameObject> gameObjects;
    private List<PhysicsObject> dynamics;
    public final boolean DEBUG_DRAW = true;
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
        for(int i = 0; i < 2; i++) {
            Random ra = new Random();
            float size = 10f;
            Vector2f r = new Vector2f(size, size);
            Player p = new Player(new Vector2f(0f, i * 20f), r);
            addObject(p);
        }
    }

    public World() {
        gameObjects = new ArrayList<>();
        dynamics = new ArrayList<>();
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
        if(g instanceof PhysicsObject) {
            dynamics.add((PhysicsObject)g);
        }
        gameObjects.add(g);

    }

    /**
     * Returns the current level
     * @return
     */
    public Level getLevel() {
        return level;
    }

    public List<PhysicsObject> getDynamics() {
        return dynamics;
    }

    /**
     * Get all the GameObjects
     * @return
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<PhysicsObject> getPhysicsObjects() {
        return null;
    }

}
