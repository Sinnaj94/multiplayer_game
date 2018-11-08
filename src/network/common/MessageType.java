package network.common;

public enum MessageType {
    CHAT((byte)0), MOVE((byte)1);
    byte b;
    MessageType(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

    public static MessageType getMessageTypeByByte(byte b) {
        // TODO: Automate: Values()?
        switch(b) {
            case (byte)0:
                return CHAT;
            case (byte)1:
                return MOVE;
        }
        return null;
    }

}
