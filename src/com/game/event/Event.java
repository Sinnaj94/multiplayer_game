package com.game.event;

import com.game.gameworld.GameObject;

/**
 * Possible Events:
 * 1: Added GameObject
 * 2: Removed GameObject
 * 3: Changed GameObject
 */
public interface Event {
    public void execute(GameObject g);
    public EventType getEventType();
}

