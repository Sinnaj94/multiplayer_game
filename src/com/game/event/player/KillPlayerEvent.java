package com.game.event.player;

import com.game.event.EventType;
import com.game.gameworld.Player;
import com.game.gameworld.World;

public class KillPlayerEvent extends PlayerEvent {
    public KillPlayerEvent(int id) {
        super(id);
    }

    @Override
    public void execute(World w) {
        Player p = (Player)w.getAccessor().get(getID());
        p.reset();
    }

    @Override
    public EventType getEventType() {
        return EventType.KILLPLAYER;
    }
}
