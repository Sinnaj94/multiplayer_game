package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class RemoveGameObjectEvent extends Event {
    public RemoveGameObjectEvent(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void execute(World w) {
        // TODO: auslagern
        w.removeObject(getGameObject());
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVE;
    }
}
