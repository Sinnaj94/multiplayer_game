package com.game.event.gameobject;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.helper.BoundingBox;

/**
 * Event for adding an Object
 */
public class AddGameObjectEvent extends GameObjectEvent {
    public GameObject getGameObject() {
        return gameObject;
    }

    private GameObject gameObject;

    /**
     * Constructor
     * @param gameObject Add actual gameObject
     */
    public AddGameObjectEvent(GameObject gameObject) {
        super(gameObject.getID());
        System.out.println("I am sending " + gameObject.getID());
        this.gameObject = gameObject;
    }

    @Override
    public void execute(World w) {
        w.addObject(getGameObject());
    }

    @Override
    public EventType getEventType() {
        return EventType.ADD;
    }
}
