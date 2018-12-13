package com.network.common;

public enum MessageType {
    CHAT((byte) 0), MOVE((byte) 1), GAME_OBJECT((byte) 2);
    byte b;

    MessageType(byte b) {
        this.b = b;
    }

    public byte getByte() {
        return b;
    }

    public static MessageType getMessageTypeByByte(byte b) {
        // TODO: Automate: Values()?
        switch (b) {
            case (byte) 0:
                return CHAT;
            case (byte) 1:
                return MOVE;
            case (byte) 2:
                return GAME_OBJECT;
        }
        return null;
    }

}
