package network.common;

import game.Input.MoveCommand;
import game.World;
import game.gameobjects.Player;
import helper.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MoveMessageHandler implements NetworkMessageHandler<MoveMessage> {
    private Player p;
    private World w;
    private MoveCommand c;
    public MoveMessageHandler() {
        c = new MoveCommand();
    }

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
        c.addGameObject(p);
    }

    public void registerWorld(World w) {
        this.w = w;
    }

    @Override
    public void handle(MoveMessage networkMessage) {
        // TODO: Why doesn't it work when overwriting the settings?
        MoveCommand test = new MoveCommand();
        test.addGameObject(p);
        test.setStrength(networkMessage.getDirection());
        w.addCommand(test);
    }
}
