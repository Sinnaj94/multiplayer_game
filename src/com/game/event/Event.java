package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

/**
 * Possible Events:
 * 1: Added GameObject
 * 2: Removed GameObject
 * 3: Changed GameObject
 */
public abstract class Event {
    private GameObject gameObject;
    public Event(GameObject gameObject) {
        this.gameObject = gameObject;
    }
    public abstract void execute(World w);
    public abstract EventType getEventType();

    public GameObject getGameObject() {
        return gameObject;
    }
}

