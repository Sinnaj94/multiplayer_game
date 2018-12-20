package com.network.common;

import com.helper.Vector2f;

public class MoveMessage implements NetworkMessage {
    private Vector2f direction;
    private int id;

    public MoveMessage(Vector2f direction) {
        this.direction = direction;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE;
    }

    public Vector2f getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "X: " + direction.getX() + ", Y:" + direction.getY();
    }
}
