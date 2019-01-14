package com.game.event.gameobject;

import com.game.event.Event;
import com.game.gameworld.GameObject;

/**
 * Possible Events:
 * 1: Added GameObject
 * 2: Removed GameObject
 * 3: Changed GameObject
 */
public abstract class GameObjectEvent implements Event {
    private int id;

    @Override
    public int getID() {
        return id;
    }

    public GameObjectEvent(int id) {
        this.id = id;
    }
}

