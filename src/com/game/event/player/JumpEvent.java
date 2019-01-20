package com.game.event.player;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.players.Player;
import com.game.gameworld.World;

/**
 * Jump Event -- Sent from Client
 */
public class JumpEvent extends PlayerEvent {
    public JumpEvent(int id) {
        super(id);
    }

    @Override
    public void execute(World w) {
        GameObject t = w.getAccessor().get(getID());
        if(t instanceof Player) {
            ((Player) t).jump();
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.PLAYERJUMP;
    }
}
