package com.network.common;

public enum MessageType {
    CHAT((byte) 0), MOVE((byte) 1), GAME_OBJECT((byte) 2), EVENT((byte) 3), COMMAND((byte) 4), CONFIG((byte) 5);
    byte b;

    MessageType(byte b) {
        this.b = b;
    }

    public byte getID() {
        return b;
    }

    public static MessageType getMessageTypeByByte(byte b) {
        for (MessageType m : MessageType.values()) {
            if (m.getID() == b) {
                return m;
            }
        }
        return null;
    }

}
