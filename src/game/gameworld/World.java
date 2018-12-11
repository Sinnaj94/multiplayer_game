package game.gameworld;

import game.Level;
import game.collectable.Collectable;
import game.generics.Collideable;
import helper.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World {
    private List<GameObject> statics;
    private List<PhysicsObject> dynamics;
    private List<Collectable> collectables;
    private List<Player> players;
    private List<GameObject> removedObjects;

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
        Random ra = new Random();
        for(int i = 0; i < 20; i++) {
            float size = 2f;
            Vector2f r = new Vector2f(size, size);
            Player p = new Player(new Vector2f(20f*i, 0f));
            p.setBounciness(ra.nextFloat() * 5);
            addObject(p);
        }
    }

    public World() {
        statics = new ArrayList<>();
        dynamics = new ArrayList<>();
        removedObjects = new ArrayList<>();
        players = new ArrayList<>();
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
            if(g instanceof Player) {
                players.add((Player)g);
            }
        } else {
            statics.add(g);
        }
    }

    public GameObject getObject(int id) {
        for(GameObject ga:statics) {
            if(id == ga.getMyID()) {
                return ga;
            }
        }
        return null;
    }

    // TODO: Event system
    public void removeObject(GameObject g) {
        removedObjects.add(g);
    }

    public void removeObjects() {
        dynamics.removeAll(removedObjects);
        removedObjects.clear();
    }

    /**
     * Returns the current level
     * @return
     */
    public Level getLevel() {
        return level;
    }

    public Collideable getCollideable() {
        return level;
    }

    public List<PhysicsObject> getDynamics() {
        return dynamics;
    }

    /**
     * Get all the GameObjects
     * @return
     */
    public List<GameObject> getStatics() {
        return statics;
    }

    public List<Player> getPlayers() {
        return players;
    }



    public List<PhysicsObject> getPhysicsObjects() {
        return null;
    }

}
