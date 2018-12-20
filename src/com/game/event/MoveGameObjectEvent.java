package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class MoveGameObjectEvent extends Event {

    public MoveGameObjectEvent(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void execute(World w) {
        //System.out.println("MOVING");
        w.getObject(getGameObject().getMyID()).setPosition(getGameObject().getPosition());
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVE;
    }
}
