package com.game.gameworld;

import com.game.Level;
import com.game.event.Event;
import com.game.event.gameobject.AddGameObjectEvent;
import com.game.event.gameobject.GameObjectEvent;
import com.game.event.gameobject.RemoveGameObjectEvent;
import com.game.event.player.GiveItemEvent;
import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.game.generics.Updateable;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Contains all the world objects and is renderable for the renderer. No Gamelogic involved
 */
public class World implements Updateable {
    private Map<Integer, Player> playerMap;
    private Map<Integer, AIPlayer> aiPlayerMap;
    private Map<Integer, Item> itemMap;
    private Map<Integer, GameObject> objects;
    private Map<Integer, Renderable> renderables;
    private Map<Integer, Bullet> bullets;
    private Map<Player, Item> playerItemCollisions;
    private Map<AIPlayer, Item> aiPlayerItemCollisions;
    private Map<Bullet, Player> bulletPlayerCollisions;
    private int targetID;

    private BlockingQueue<Event> gameObjectEvents;


    private List<Integer> removedObjects;
    public final boolean DEBUG_DRAW = false;
    public static final int TILE_SIZE = 64;
    public static final int CHUNK_TILES = 8;
    public static final int CHUNK_SIZE = TILE_SIZE * CHUNK_TILES;
    public static int DEATHZONE = TILE_SIZE * CHUNK_TILES + TILE_SIZE;
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

    public GameObject getTarget() {
        return objects.get(targetID);
    }

    public Accessor getAccessor() {
        return accessor;
    }

    public World() {
        accessor = new Accessor();
        isLoaded = false;
        playerMap = new HashMap<>();
        aiPlayerMap = new HashMap<>();
        objects = new HashMap<>();
        renderables = new HashMap<>();
        itemMap = new HashMap<>();
        bullets = new HashMap<>();
        gameObjectEvents = new ArrayBlockingQueue<Event>(100000);
        playerItemCollisions = new HashMap<>();
        aiPlayerItemCollisions = new HashMap<>();
        bulletPlayerCollisions = new HashMap<>();
        // TODO: DELETE
        removedObjects = new ArrayList<>();
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
        if (g instanceof Player) {
            if(g instanceof AIPlayer) {
                aiPlayerMap.put(g.getID(), (AIPlayer)g);
            } else {
                playerMap.put(g.getID(), (Player)g);
            }
        } else if(g instanceof Item) {
            itemMap.put(g.getID(), (Item)g);
        } else if(g instanceof Bullet) {
            bullets.put(g.getID(), (Bullet)g);
        }
        renderables.putAll(objects);
        return g;
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
    public void removeObject(int id) {
        System.out.println("REMOVE ATTEMPT " + id);
        removedObjects.add(id);
    }

    /**
     * Returns the current level
     *
     * @return
     */
    public Level getLevel() {
        return level;
    }

    private Collideable getCollideable() {
        return level;
    }

    /**
     * Get all the GameObjects
     *
     * @return
     */
    private Map<Integer, GameObject> getObjects() {
        return objects;
    }

    private void removeObjects() {
        if (removedObjects.isEmpty()) return;
        for (Integer i : removedObjects) {
            System.out.println("Removing Object with ID " + i);
            objects.remove(i);
            playerMap.remove(i);
            aiPlayerMap.remove(i);
            itemMap.remove(i);
            renderables.remove(i);
            bullets.remove(i);
        }
        removedObjects.clear();
    }

    private void executeEvents() {
        // Execute all gameObjectEvents and then delete
        if (gameObjectEvents.isEmpty()) return;
        for (Event e : gameObjectEvents) {
            e.execute(this);
        }
        gameObjectEvents.clear();
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update() {
        // Remove Objects.. Clean up!
        executeEvents();
        removeObjects();

        for (GameObject gameObject : getObjects().values()) {
            gameObject.update();
        }
        // Update Collisions
        playerItemCollisions.clear();
        aiPlayerItemCollisions.clear();
        for(Item item:itemMap.values()) {
            for(Player player:playerMap.values()) {
                if (player.getBoundingBox().intersects(item.getBoundingBox())) {
                    playerItemCollisions.put(player, item);
                }
            }
            for(AIPlayer aiPlayer:aiPlayerMap.values()) {
                if (aiPlayer.getBoundingBox().intersects(item.getBoundingBox())) {
                    aiPlayerItemCollisions.put(aiPlayer, item);
                }
            }
        }
        bulletPlayerCollisions.clear();
        for(Bullet b:bullets.values()) {
            for(Player player:playerMap.values()) {
                if (player.getBoundingBox().intersects(b.getBoundingBox())) {
                    bulletPlayerCollisions.put(b, player);
                }
            }
            for(AIPlayer aiPlayer:aiPlayerMap.values()) {
                if (aiPlayer.getBoundingBox().intersects(b.getBoundingBox())) {
                    bulletPlayerCollisions.put(b, aiPlayer);
                }
            }
        }

        // Update found Items (remove them) :P
        for(Item item:itemMap.values()) {
            if(item.isGiven()) {
                //accessor.remove(item.getID());
                accessor.addEvent(new GiveItemEvent(item.playerID, item.getID()));
                accessor.addEvent(new RemoveGameObjectEvent(item.getID()));
            }
        }

        currentTime = System.currentTimeMillis() - lastTime;
        if (currentTime >= timeStep) {
            time.tick();
            lastTime = System.currentTimeMillis();
        }
    }

    /**
     * Class for accessing data
     */
    public class Accessor {
        private BlockingQueue<Event> synchronizedEvents;

        public void addEvent(Event e) {
            synchronizedEvents.add(e);
            gameObjectEvents.add(e);
        }

        public void addLocalEvent(Event e) {
            gameObjectEvents.add(e);
        }

        public BlockingQueue<Event> getSynchronizedEvents() {
            return synchronizedEvents;
        }

        public Accessor() {
            synchronizedEvents = new ArrayBlockingQueue<Event>(100000);
        }
        public GameObject get(int id) {
            return getObject(id);
        }

        public List<GameObject> get() {
            return new ArrayList<>(objects.values());
        }

        public List<Player> getPlayers() {
            return new ArrayList<>(playerMap.values());
        }

        public List<Player> getAllPlayers() {
            List<Player> r = new ArrayList<>();
            r.addAll(aiPlayerMap.values());
            r.addAll(playerMap.values());
            return r;
        }

        public GameObject add(GameObject g) {
            addEvent(new AddGameObjectEvent(g));
            return g;
        }

        public int remove(int id) {
            addEvent(new RemoveGameObjectEvent(id));
            return id;
        }

        public Player addPlayer(String username) {
            AddGameObjectEvent e = new AddGameObjectEvent(new Player(username));
            addEvent(e);
            return (Player)e.getGameObject();
        }

        public Map<Bullet, Player> getBulletPlayerCollisions() {
            return bulletPlayerCollisions;
        }

        public Collection<Bullet> getBullets() {
            return bullets.values();
        }

        public GameObject getTarget() {
            return World.this.getTarget();
        }

        public void setTarget(int id) {
            World.this.setTargetID(id);
        }

        public Level getLevel() {
            return level;
        }

        public Map<Integer, Renderable> getRenderables() {
            return renderables;
        }



        public Map<Player, Item> getPlayerItemCollisions() {
            Map<Player, Item> _return = new HashMap<>();
            _return.putAll(playerItemCollisions);
            _return.putAll(aiPlayerItemCollisions);
            return _return;
        }

        public boolean exists(int id) {
            return existsObject(id);
        }
    }
}
