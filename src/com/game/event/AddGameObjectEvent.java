package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class AddGameObjectEvent implements Event {
    @Override
    public void execute(GameObject g) {
        // TODO: auslagern
        World.getInstance().addObject(g);
    }

    @Override
    public EventType getEventType() {
        return EventType.ADD;
    }
}
