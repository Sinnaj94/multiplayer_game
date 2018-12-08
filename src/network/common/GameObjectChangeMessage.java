package network.common;

public class GameObjectChangeMessage implements NetworkMessage {
    @Override
    public MessageType getMessageType() {
        return MessageType.OBJECT;
    }
}
