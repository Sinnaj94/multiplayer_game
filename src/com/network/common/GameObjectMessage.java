package com.network.common;

import com.game.gameworld.GameObject;
import com.game.gameworld.PhysicsObject;

public class GameObjectMessage implements NetworkMessage {
    private GameObject physicsObject;
    public GameObjectMessage(GameObject p) {
        this.physicsObject = p;
    }

    public GameObject getGameObject() {
        return physicsObject;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_OBJECT;
    }
}
