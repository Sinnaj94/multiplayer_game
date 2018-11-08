package network.common;

import helper.Vector2;

public class MoveMessage implements NetworkMessage {
    private Vector2 direction;

    public MoveMessage(Vector2 direction) {
        this.direction = direction;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE;
    }

    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "X: " + direction.getX() + ", Y:" + direction.getY();
    }
}
