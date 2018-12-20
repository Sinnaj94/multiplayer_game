package com.game.gameworld;

import com.game.Level;
import com.game.collectable.Collectable;
import com.game.event.Event;
import com.game.generics.Collideable;
import com.helper.Vector2f;

import java.util.*;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World {
    /*private List<GameObject> statics;
    private List<PhysicsObject> dynamics;
    private List<Collectable> collectables;
    private List<Player> players;
    private List<GameObject> removedObjects;
    private List<Event> events;*/
    private Map<Integer, PhysicsObject> dynamics;
    private Map<Integer, Player> playerMap;
    private Map<Integer, GameObject> statics;

    private List<Integer> removedObjects;
    public final boolean DEBUG_DRAW = true;
    public static final int TILE_SIZE = 32;
    public static final int CHUNK_TILES = 8;
    public static final int CHUNK_SIZE = TILE_SIZE * CHUNK_TILES;
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
        for(int i = 0; i < 1; i++) {
            float size = 16f;
            Vector2f r = new Vector2f(size, size);
            Player p = new Player(new Vector2f(0f, -100f), r);
            //p.setMaxFallingSpeed(ra.nextFloat() * 20f + 2f);
            p.setBounciness(0);
            addObject(p);
        }
    }

    public World() {
        /*statics = new ArrayList<>();
        dynamics = new ArrayList<>();
        removedObjects = new ArrayList<>();
        players = new ArrayList<>();
        events = new ArrayList<>();*/
        dynamics = new HashMap<>();
        playerMap = new HashMap<>();
        statics = new HashMap<>();
        removedObjects = new ArrayList<>();
        level = new Level("test.png", "res/tilesets/forest_tiles.json");
    }

    /*public void addEvent(Event e) {
        events.add(e);
    }*/

    /*public List getEvents() {
        return events;
    }*/

    private void build() {
        level.build();
    }

    /**
     * Adds a Gameobject
     * @return The added Gameobject, so it will work further
     */
    public void addObject(GameObject g) {
        if(g instanceof PhysicsObject) {
            dynamics.put(g.getMyID(), (PhysicsObject)g);
            if(g instanceof Player) {
                playerMap.put(g.getMyID(),(Player)g);
            }
        } else {
            statics.put(g.getMyID(), g);
        }
    }

    public GameObject getObject(int id) {
        return statics.get(id);
    }

    public boolean existsObject(int id) {
        return statics.containsKey(id);
    }

    // TODO: Event system
    public void removeObject(int i) {
        removedObjects.add(i);
    }

    public void removeObject(GameObject g) {
        removedObjects.add(g.getMyID());
    }

    public void removeObjects() {
        for(Integer i:removedObjects) {
            dynamics.remove(i);
        }
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

    public Map<Integer, PhysicsObject> getDynamics() {
        return dynamics;
    }

    /**
     * Get all the GameObjects
     * @return
     */
    public Map<Integer, GameObject> getStatics() {
        return statics;
    }

    public Map<Integer, Player> getPlayers() {
        return playerMap;
    }



    public List<PhysicsObject> getPhysicsObjects() {
        return null;
    }

}
