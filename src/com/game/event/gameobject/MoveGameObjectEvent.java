package com.game.event.gameobject;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.helper.Vector2f;

public class MoveGameObjectEvent extends GameObjectEvent {
    public Vector2f getPosition() {
        return position;
    }

    private Vector2f position;
    public MoveGameObjectEvent(int id, Vector2f position) {
        super(id);
        this.position = position;
    }

    @Override
    public void execute(World w) {
        try {
            w.getAccessor().get(getID()).setPosition(getPosition());
        } catch (NullPointerException e) {
            System.out.println("Object doesnt exist yet.");
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVE;
    }
}
