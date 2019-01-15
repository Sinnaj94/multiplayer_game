package com.game.event.player;

import com.game.event.EventType;
import com.game.gameworld.Player;
import com.game.gameworld.World;

public class HitPlayerEvent extends PlayerEvent {

    public HitPlayerEvent(int id) {
        super(id);
    }

    @Override
    public void execute(World w) {
        ((Player)w.getAccessor().get(getID())).hit(1);
    }

    @Override
    public EventType getEventType() {
        return EventType.HITPLAYER;
    }
}
