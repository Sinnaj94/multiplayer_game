package com.network.common;

import com.game.input.Command;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;

public class CommandMessageHandler implements NetworkMessageHandler<CommandMessage> {
    private Command command;

    public CommandMessageHandler() {

    }

    @Override
    public void sendMessage(CommandMessage commandMessage, MyDataOutputStream dos) {
        try {
            dos.write(commandMessage.getMessageType().getByte());
            dos.writeCommand(commandMessage.getCommand());
        } catch(IOException e) {

        }
    }

    @Override
    public CommandMessage getNetworkMessage(MyDataInputStream dis) {
        try {
            return new CommandMessage(dis.readCommand());
        } catch(IOException e) {

        }
        return null;
    }

    @Override
    public void handle(CommandMessage commandMessage) {
        commandMessage.getCommand().execute();
    }
}
