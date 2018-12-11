package network.common;

import game.gameworld.GameObject;

public class GameObjectMessage implements NetworkMessage {
    private GameObject gameObject;
    public GameObjectMessage(GameObject g) {
        this.gameObject = g;
    }

    public GameObject toGameObject() {
        return gameObject;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GAME_OBJECT;
    }
}
