package network.common;

import helper.Vector2F;

public class MoveMessage implements NetworkMessage {
    private Vector2F direction;

    public MoveMessage(Vector2F direction) {
        this.direction = direction;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.MOVE;
    }

    public Vector2F getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "X: " + direction.getX() + ", Y:" + direction.getY();
    }
}
