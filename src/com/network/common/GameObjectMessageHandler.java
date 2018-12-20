package com.network.common;

import com.game.event.AddGameObjectEvent;
import com.game.event.MoveGameObjectEvent;
import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.game.gameworld.SynchronizedGameObject;
import com.game.gameworld.World;
import com.helper.Vector2f;
import com.network.client.ClientGameLogic;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameObjectMessageHandler implements NetworkMessageHandler<GameObjectMessage> {
    private int commandCount;
    @Override
    public void sendMessage(GameObjectMessage objectMessage, MyDataOutputStream dos) {
        try {
            GameObject t = objectMessage.getGameObject();
            // First write ID
            dos.write(MessageType.GAME_OBJECT.getByte());
            dos.writeInt(t.getMyID());
            // Then write Position
            // X
            dos.writeFloat(t.getPosition().getX());
            // Y
            dos.writeFloat(t.getPosition().getY());
            // Then we also need the size
            dos.writeFloat(t.getSize().getX());
            dos.writeFloat(t.getSize().getY());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameObjectMessage getNetworkMessage(MyDataInputStream dis) {
        try {
            // First write ID
            int id = dis.readInt();
            // Then write Position
            // X
            Vector2f position = new Vector2f(dis.readFloat(), dis.readFloat());
            // Then we also need the size
            Vector2f size = new Vector2f(dis.readFloat(), dis.readFloat());
            GameObject r = new SynchronizedGameObject(position,size,id);
            return new GameObjectMessage(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(GameObjectMessage objectMessage) {
        int id = objectMessage.getGameObject().getMyID();
        System.out.println(commandCount++);
        if(!World.getInstance().existsObject(id)) {
            World.getInstance().addEvent(new AddGameObjectEvent(new Player(objectMessage.getGameObject().getPosition())));
        } else {
            World.getInstance().addEvent(new MoveGameObjectEvent(objectMessage.getGameObject()));
        }
    }

}
