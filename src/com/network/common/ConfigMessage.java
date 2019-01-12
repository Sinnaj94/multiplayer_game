package com.network.common;

public class ConfigMessage implements NetworkMessage {
    public int getPlayerID() {
        return playerID;
    }

    public boolean isSuccessfulConnect() {
        return successfulConnect;
    }

    private boolean successfulConnect;

    private int playerID;
    public ConfigMessage(int playerID) {
        this.playerID = playerID;
        successfulConnect = true;
    }

    public ConfigMessage() {
        this.successfulConnect = false;
        this.playerID = -1;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CONFIG;
    }

    public String toString() {
        return String.format("successfull connect: %b id: %d", isSuccessfulConnect(), getPlayerID());
    }
}
