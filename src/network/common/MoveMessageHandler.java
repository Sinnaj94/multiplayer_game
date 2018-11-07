package network.common;

import helper.Vector2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MoveMessageHandler implements NetworkMessageHandler<MoveMessage>  {
    @Override
    public void sendMessage(NetworkMessage networkMessage, DataOutputStream dos) {
        MoveMessage m = (MoveMessage)networkMessage;
        try {
            dos.write(m.getMessageType().getByte());
            dos.writeFloat(m.getDirection().getX());
            dos.writeFloat(m.getDirection().getY());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MoveMessage getNetworkMessage(DataInputStream dis) {
        try {
            float x = dis.readFloat();
            float y = dis.readFloat();
            return new MoveMessage(new Vector2(x, y));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(MoveMessage networkMessage) {

    }
}
