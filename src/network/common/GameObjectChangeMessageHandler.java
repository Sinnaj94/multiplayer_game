package network.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class GameObjectChangeMessageHandler implements NetworkMessageHandler<GameObjectChangeMessage> {
    @Override
    public void sendMessage(GameObjectChangeMessage objectMessage, DataOutputStream dos) {

    }

    @Override
    public GameObjectChangeMessage getNetworkMessage(DataInputStream dis) {
        return null;
    }

    @Override
    public void handle(GameObjectChangeMessage objectMessage) {

    }
}
