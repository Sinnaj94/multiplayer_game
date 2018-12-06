package network.common;

public class ChangesMessage implements NetworkMessage {
    private String newLevel;

    public ChangesMessage(String newLevel) {
        this.newLevel = newLevel;
    }

    public String getNewLevel() {
        return this.newLevel;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CHANGES;
    }
}
