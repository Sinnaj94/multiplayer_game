package network.common;

import game.gameworld.GameObject;
import game.gameworld.SynchronizedGameObject;
import game.gameworld.World;
import helper.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameObjectMessageHandler implements NetworkMessageHandler<GameObjectMessage> {
    @Override
    public void sendMessage(GameObjectMessage objectMessage, DataOutputStream dos) {
        try {
            GameObject t = objectMessage.toGameObject();
            // First write ID
            dos.write(objectMessage.getMessageType().getByte());
            dos.writeInt(t.getMyID());
            // Write Position
            dos.writeFloat(t.getPosition().getX());
            dos.writeFloat(t.getPosition().getY());
            // Write Size
            dos.writeFloat(objectMessage.toGameObject().getWidth());
            dos.writeFloat(objectMessage.toGameObject().getHeight());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameObjectMessage getNetworkMessage(DataInputStream dis) {
        try {
            int id = dis.readInt();
            // TODO: Check if it exists in the world
            // read position
            float x = dis.readFloat();
            float y = dis.readFloat();
            // read size
            float sx = dis.readFloat();
            float sy = dis.readFloat();
            return new GameObjectMessage(new SynchronizedGameObject(new Vector2f(x, y), new Vector2f(sx, sy), id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(GameObjectMessage objectMessage) {
        // TODO: Change the world
        GameObject ex = World.getInstance().getObject(objectMessage.toGameObject().getMyID());
        if(ex != null) {
            ex.setPosition(objectMessage.toGameObject().getPosition());
        } else {
            World.getInstance().addObject(objectMessage.toGameObject());
        }
    }
}
