package com.network.common;

import com.game.gameworld.players.Player;
import com.game.event.player.Command;
import com.game.gameworld.World;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandMessageHandler implements NetworkMessageHandler<CommandMessage> {
    private Command command;
    private Player player;
    private List<Manager.Accessor> managers;
    private World.Accessor accessor;

    public CommandMessageHandler() {
        managers = new ArrayList<>();
        accessor = World.getInstance().getAccessor();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addAccessor(Manager.Accessor accessor) {
        managers.add(accessor);
    }

    @Override
    public void sendMessage(CommandMessage commandMessage, MyDataOutputStream dos) throws IOException {
        dos.writeByte(MessageType.COMMAND.getID());
        dos.writeCommand(commandMessage.getCommand());
    }

    @Override
    public CommandMessage getNetworkMessage(MyDataInputStream dis) throws IOException {
        Command command = dis.readCommand();
        return new CommandMessage(command);
    }

    @Override
    public void handle(CommandMessage commandMessage) {
        commandMessage.getCommand().execute(accessor);
    }
}
