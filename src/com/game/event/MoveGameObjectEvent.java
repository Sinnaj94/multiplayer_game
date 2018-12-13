package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class MoveGameObjectEvent implements Event {
    @Override
    public void execute(GameObject g) {
        GameObject existant = World.getInstance().getObject(g.getMyID());
        existant.setPosition(g.getPosition());
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVE;
    }
}
