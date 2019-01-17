package com.game.event.gameobject;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class RemoveGameObjectEvent extends GameObjectEvent {
    public RemoveGameObjectEvent(int id) {
        super(id);
    }

    @Override
    public void execute(World w) {
        System.out.println("Removing " + w.getAccessor().get(getID()));
        w.removeObject(getID());
    }

    @Override
    public EventType getEventType() {
        return EventType.REMOVE;
    }
}
