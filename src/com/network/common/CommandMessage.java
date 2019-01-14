package com.network.common;

import com.game.event.player.Command;

public class CommandMessage implements NetworkMessage {
    private Command c;

    public CommandMessage(Command c) {
        this.c = c;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.COMMAND;
    }

    public Command getCommand() {
        return c;
    }
}
