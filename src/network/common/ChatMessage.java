package network.common;

public class ChatMessage implements NetworkMessage {
    private String message;
    public ChatMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CHAT;
    }
}
