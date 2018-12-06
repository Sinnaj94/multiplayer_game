package network.common;

import game.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChangesMessageHandler implements NetworkMessageHandler<ChangesMessage> {
    private World w;

    @Override
    public void sendMessage(NetworkMessage networkMessage, DataOutputStream dos) {
        try {
            ChangesMessage c = (ChangesMessage) networkMessage;
            dos.write(c.getMessageType().getByte());
            dos.writeUTF(c.getNewLevel());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ChangesMessage getNetworkMessage(DataInputStream dis) {
        try {
            String newLevel = dis.readUTF();
            return new ChangesMessage(newLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerWorld(World w) {
        this.w = w;
    }

    @Override
    public void handle(ChangesMessage networkMessage) {

    }
}
