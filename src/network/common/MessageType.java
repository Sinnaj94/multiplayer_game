package network.common;

public enum MessageType {
    CHAT((byte)0);
    byte b;
    MessageType(byte i) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

    public static MessageType getMessageTypeByByte(byte b) {
        switch(b) {
            case (byte)0:
                return CHAT;
        }
        return null;
    }

}
