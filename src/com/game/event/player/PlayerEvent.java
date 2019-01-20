package com.game.event.player;
import com.game.event.Event;

/**
 * Basic abstract class for Player Events
 */
public abstract class PlayerEvent implements Event {
    private int id;

    @Override
    public int getID() {
        return id;
    }

    public PlayerEvent(int id) {
        this.id = id;
    }
}
