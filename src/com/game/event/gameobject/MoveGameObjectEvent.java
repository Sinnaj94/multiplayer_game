package com.game.event.gameobject;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.helper.Vector2f;

/**
 * Move object Event
 */
public class MoveGameObjectEvent extends GameObjectEvent {
    public Vector2f getPosition() {
        return position;
    }

    private Vector2f position;

    /**
     *
     * @param id GameObject ID
     * @param position new Position
     */
    public MoveGameObjectEvent(int id, Vector2f position) {
        super(id);
        this.position = position;
    }

    @Override
    public void execute(World w) {
        try {
            w.getAccessor().get(getID()).setPosition(getPosition());
        } catch (NullPointerException e) {
            System.out.println(String.format("Object %d doesnt exist yet.", getID()));
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVE;
    }
}
