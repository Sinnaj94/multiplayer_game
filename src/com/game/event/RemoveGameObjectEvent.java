package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class RemoveGameObjectEvent implements Event {
    @Override
    public void execute(GameObject g) {
        // TODO: auslagern
        World.getInstance().removeObject(g.getMyID());
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVE;
    }
}
