package com.game.event;

import com.game.gameworld.World;

/**
 * Event interface - it can be executed in a World.
 */
public interface Event {
    void execute(World w);
    EventType getEventType();
    int getID();
}
