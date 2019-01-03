package com.network.common;

import com.game.gameworld.Player;
import com.game.input.Command;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandMessageHandler implements NetworkMessageHandler<CommandMessage> {
    private Command command;
    private Player player;
    private List<MyDataOutputStream> outputStreams;

    public CommandMessageHandler() {
        outputStreams = new ArrayList<>();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addOutputStream(MyDataOutputStream m) {
        outputStreams.add(m);
    }

    @Override
    public void sendMessage(CommandMessage commandMessage, MyDataOutputStream dos) {
        try {
            dos.writeByte(commandMessage.getMessageType().getID());
            dos.writeCommand(commandMessage.getCommand());
            dos.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public CommandMessage getNetworkMessage(MyDataInputStream dis) {
        try {
            Command command = dis.readCommand();
            return new CommandMessage(command);
        } catch (IOException e) {

        }
        return null;
    }

    @Override
    public void handle(CommandMessage commandMessage) {
        // Send the Command Message to all clients (the ones that registered...)
        if (outputStreams.size() > 0) {
            for (MyDataOutputStream m : outputStreams) {
                sendMessage(commandMessage, m);
            }
        }
        commandMessage.getCommand().execute();
    }
}
