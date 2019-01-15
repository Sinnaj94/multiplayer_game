package com.game.event.player;

import com.game.event.EventType;
import com.game.event.gameobject.AddGameObjectEvent;
import com.game.gameworld.*;
import com.helper.Vector2f;

public class ShootEvent extends PlayerEvent {
    public Vector2f getDirection() {
        return direction;
    }

    private Vector2f direction;
    public ShootEvent(int id, Vector2f direction) {
        super(id);
        this.direction = direction;
    }

    @Override
    public void execute(World w) {
        Player p = (Player)w.getAccessor().get(getID());
        direction.normalize().multiply(10f);
        p.setShoot(direction);
    }

    @Override
    public EventType getEventType() {
        return EventType.SHOOT;
    }
}
