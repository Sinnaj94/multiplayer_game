package com.network.common;

import com.game.gameworld.GameObject;
import com.game.gameworld.PhysicsObject;

public class GameObjectMessage implements NetworkMessage {
    private PhysicsObject physicsObject;
    public GameObjectMessage(PhysicsObject p) {
        this.physicsObject = p;
    }

    public PhysicsObject toPhysicsObject() {
        return physicsObject;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_OBJECT;
    }
}
