package com.game.event.gameobject;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.helper.BoundingBox;

public class AddGameObjectEvent extends GameObjectEvent {
    public GameObject getGameObject() {
        return gameObject;
    }

    private GameObject gameObject;
    public AddGameObjectEvent(GameObject gameObject) {
        super(gameObject.getID());
        this.gameObject = gameObject;
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
}
