package network.common;

import game.gameobjects.Player;
import helper.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MoveMessageHandler implements NetworkMessageHandler<MoveMessage> {
    private Player p;

    @Override
    public void sendMessage(NetworkMessage networkMessage, DataOutputStream dos) {
        try {
            MoveMessage m = (MoveMessage) networkMessage;
            dos.write(m.getMessageType().getByte());
            dos.writeFloat(m.getDirection().getX());
            dos.writeFloat(m.getDirection().getY());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MoveMessage getNetworkMessage(DataInputStream dis) {
        try {
            float x = dis.readFloat();
            float y = dis.readFloat();
            return new MoveMessage(new Vector2f(x, y));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerPlayer(Player p) {
        this.p = p;
    }

    @Override
    public void handle(MoveMessage networkMessage) {
        System.out.println(networkMessage.toString());
    }
}
