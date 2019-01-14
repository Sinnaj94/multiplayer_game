package com.game.event;

import com.game.gameworld.World;

public interface Event {
    void execute(World w);
    EventType getEventType();
    int getID();
}
