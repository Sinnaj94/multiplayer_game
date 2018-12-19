package com.network.common;

import com.game.gameworld.GameObject;

public class GameObjectMessage implements NetworkMessage {
    private GameObject gameObject;
    private boolean isTarget;
    public GameObjectMessage(GameObject p) {
        this.gameObject = p;
        isTarget = false;
    }

    public GameObjectMessage(GameObject p, boolean isTarget) {
        this.gameObject = p;
        this.isTarget = isTarget;
    }

    public GameObject getGameObject() {
        return gameObject;
    }


    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_OBJECT;
    }
}
