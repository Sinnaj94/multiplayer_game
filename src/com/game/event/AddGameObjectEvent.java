package com.game.event;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class AddGameObjectEvent extends Event {
    private boolean isMainPlayer;

    public AddGameObjectEvent(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void execute(World w) {
        // TODO: auslagern
        w.addObject(getGameObject());
    }

    @Override
    public EventType getEventType() {
        return EventType.ADD;
    }

    public boolean isMainPlayer() {
        return isMainPlayer;
    }
}
