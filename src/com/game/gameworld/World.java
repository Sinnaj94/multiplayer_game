package com.game.gameworld;

import com.game.Level;
import com.game.event.Event;
import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.game.generics.Updateable;
import com.helper.Vector2f;

import java.util.*;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World implements Updateable {
    private Map<Integer, PhysicsObject> dynamics;
    private Map<Integer, Player> playerMap;
    private Map<Integer, GameObject> statics;
    private Map<Integer, Renderable> renderables;

    private List<Integer> removedObjects;
    private List<Event> eventsList;
    public final boolean DEBUG_DRAW = true;
    public static final int TILE_SIZE = 32;
    public static final int CHUNK_TILES = 8;
    public static final int CHUNK_SIZE = TILE_SIZE * CHUNK_TILES;
    private Level level;
    private static World instance;
    private Time time;

    private double currentTime;
    private double lastTime;
    private final double timeStep = 1000;
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
        renderables = new HashMap<>();
        // TODO: DELETE
        removedObjects = new ArrayList<>();
        eventsList = new ArrayList<>();
        time = new Time();
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
    public GameObject addObject(GameObject g) {
        if(g instanceof PhysicsObject) {
            dynamics.put(g.getMyID(), (PhysicsObject)g);
            if(g instanceof Player) {
                playerMap.put(g.getMyID(),(Player)g);
            }
        } else {
            statics.put(g.getMyID(), g);
        }
        renderables.putAll(dynamics);
        renderables.putAll(playerMap);
        renderables.putAll(statics);
        return g;
    }

    public GameObject getObject(int id) {
        return dynamics.get(id);
    }

    public boolean existsObject(int id) {
        return dynamics.containsKey(id);
    }

    // TODO: Event system
    public void removeObject(int i) {
        removedObjects.add(i);
    }

    public void removeObject(GameObject g) {
        removedObjects.add(g.getMyID());
    }

    public void removeObjects() {
        if(removedObjects.isEmpty()) return;
        for(Integer i:removedObjects) {
            dynamics.remove(i);
            renderables.remove(i);
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

    public Map<Integer, Renderable> getRenderables() {
        return renderables;
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

    public void addEvent(Event e) {
        eventsList.add(e);
    }

    public void executeEvents() {
        // Execute all events and then delete
        if(eventsList.isEmpty()) return;
        for(Event e:eventsList) {
            e.execute(this);
        }
        eventsList.clear();
    }

    public GameObject getObservable() {
        return playerMap.get(0);
    }

    @Override
    public void update() {
        for(PhysicsObject gameObject:getDynamics().values()) {
            gameObject.update();
        }
        currentTime = System.currentTimeMillis() - lastTime;
        //System.out.println(currentTime);
        if(currentTime >= timeStep) {
            time.tick();
            //System.out.println(time.toString());
            lastTime = System.currentTimeMillis();
        }
        //removeObjects();
        executeEvents();
        //lastTime = System.currentTimeMillis();
    }
}
