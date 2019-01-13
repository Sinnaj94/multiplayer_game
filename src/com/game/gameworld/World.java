package com.game.gameworld;

import com.game.Level;
import com.game.event.Event;
import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.game.generics.Updateable;

import java.util.*;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World implements Updateable {
    private Map<Integer, Player> playerMap;
    private Map<Integer, GameObject> objects;
    private Map<Integer, Renderable> renderables;
    private int targetID;

    private List<Integer> removedObjects;
    private List<Event> eventsList;
    public final boolean DEBUG_DRAW = false;
    public static final int TILE_SIZE = 64;
    public static final int CHUNK_TILES = 8;
    public static final int CHUNK_SIZE = TILE_SIZE * CHUNK_TILES;
    private Level level;
    private static World instance;
    private Time time;

    private double currentTime;
    private double lastTime;
    private final double timeStep = 1000;

    private Accessor accessor;

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    private boolean isLoaded;

    /**
     * Singleton implementation, there can only be ONE world object.
     */
    public static synchronized World getInstance() {
        if (World.instance == null) {
            World.instance = new World();
        }
        return World.instance;
    }

    public void setTarget(GameObject target) {
        //this.target = (Player) target;
    }

    public void setTargetID(int objectID) {
        this.targetID = objectID;
    }

    public Player getTarget() {
        return playerMap.get(targetID);
    }

    public Accessor getAccessor() {
        return accessor;
    }

    public World() {
        accessor = new Accessor();
        isLoaded = false;
        playerMap = new HashMap<>();
        objects = new HashMap<>();
        renderables = new HashMap<>();
        // TODO: DELETE
        removedObjects = new ArrayList<>();
        eventsList = new ArrayList<>();
        time = new Time();
        level = new Level("test.png", "res/tilesets/forest_tiles.json");
    }

    /**
     * Adds a Gameobject
     *
     * @return The added Gameobject, so it will work further
     */
    public GameObject addObject(GameObject g) {
        System.out.println(String.format("Added %s with ID %d", g.getClass().getName(), g.getID()));
        objects.put(g.getID(), g);
        if (g instanceof Player) { ;
            playerMap.put(g.getID(), (Player)g);
        } else if(g instanceof Item) {

        }
        renderables.putAll(objects);
        return g;
    }

    /**
     * Spawns a new Player
     *
     * @return Player instance
     */
    public Player spawnPlayer(String username) {
        return (Player) addObject(new Player(username));
    }

    /**
     * Return an object by a given ID
     *
     * @param id GameObject ID
     * @return GameObject with ID, null if not existant
     */
    private GameObject getObject(int id) {
        return objects.get(id);
    }

    /**
     * Check if GameObject with given ID exists
     *
     * @param id GameObject ID
     * @return true if exists, false if not
     */
    private boolean existsObject(int id) {
        return objects.containsKey(id);
    }

    /**
     * Add Object to Remove List
     *
     * @param id GameObject ID
     */
    private void removeObject(int id) {
        System.out.println("REMOVE ATTEMPT " + id);
        removedObjects.add(id);
    }

    /**
     * Remove all objects in Remove list and clear the list
     */
    private void removeObjects() {
        if (removedObjects.isEmpty()) return;
        for (Integer i : removedObjects) {
            System.out.println("Removing Object with ID " + i);
            objects.remove(i);
            playerMap.remove(i);
            renderables.remove(i);
        }
        removedObjects.clear();
    }

    /**
     * Returns the current level
     *
     * @return
     */
    public Level getLevel() {
        return level;
    }

    public Collideable getCollideable() {
        return level;
    }

    public Map<Integer, Renderable> getRenderables() {
        return renderables;
    }

    /**
     * Get all the GameObjects
     *
     * @return
     */
    public Map<Integer, GameObject> getObjects() {
        return objects;
    }

    public Map<Integer, Player> getPlayers() {
        return playerMap;
    }


    public List<PhysicsObject> getPhysicsObjects() {
        return null;
    }

    public void addEvent(Event e) {
        eventsList.add(e);
    }

    public void executeEvents() {
        // Execute all events and then delete
        if (eventsList.isEmpty()) return;
        for (Event e : eventsList) {
            e.execute(this);
        }
        eventsList.clear();
    }

    @Override
    public void update() {
        // Remove Objects..
        removeObjects();
        for (GameObject gameObject : getObjects().values()) {
            gameObject.update();
        }
        currentTime = System.currentTimeMillis() - lastTime;
        if (currentTime >= timeStep) {
            time.tick();
            lastTime = System.currentTimeMillis();
        }
        executeEvents();
    }

    /**
     * Class for accessing data
     */
    public class Accessor {
        public GameObject get(int id) {
            return getObject(id);
        }

        public Collection<GameObject> get() {
            return objects.values();
        }

        public GameObject add(GameObject g) {
            return addObject(g);
        }

        public void remove(int id) {
            removeObject(id);
        }

        public boolean exists(int id) {
            return existsObject(id);
        }
    }
}
