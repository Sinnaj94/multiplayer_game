package com.network.common;

import com.game.gameworld.World;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;

public class ConfigMessageHandler implements NetworkMessageHandler<ConfigMessage> {
    private World.Accessor accessor;
    public ConfigMessageHandler() {
        accessor = World.getInstance().getAccessor();
    }

    @Override
    public void sendMessage(ConfigMessage networkMessage, MyDataOutputStream dos) throws IOException {
        dos.writeByte(MessageType.CONFIG.getID());
        dos.writeBoolean(networkMessage.isSuccessfulConnect());
        dos.writeInt(networkMessage.getPlayerID());
    }

    @Override
    public ConfigMessage getNetworkMessage(MyDataInputStream dis) throws IOException {
        boolean successfulConnect = dis.readBoolean();
        int playerID = dis.readInt();
        if(!successfulConnect) {
            return new ConfigMessage();
        }
        return new ConfigMessage(playerID);
    }

    @Override
    public void handle(ConfigMessage networkMessage) {
        System.out.println(networkMessage);
        if(!networkMessage.isSuccessfulConnect()) {
            System.exit(-1);
        } else {
            accessor.setTarget(networkMessage.getPlayerID());
        }
    }
}
