package com.network.common;

import com.game.gameworld.GameObject;
import com.game.gameworld.PhysicsObject;
import com.game.gameworld.SynchronizedGameObject;
import com.game.gameworld.World;
import com.helper.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameObjectMessageHandler implements NetworkMessageHandler<GameObjectMessage> {
    @Override
    public void sendMessage(GameObjectMessage objectMessage, DataOutputStream dos) {
        try {
            PhysicsObject t = objectMessage.toPhysicsObject();
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
            // We also need the speed
            dos.writeFloat(t.getCurrentSpeed().getX());
            dos.writeFloat(t.getCurrentSpeed().getY());
            // We also need the bounciness
            dos.writeFloat(t.getImpulse().getX());
            dos.writeFloat(t.getImpulse().getY());
            // We also need the
            dos.writeFloat(t.getMaxFallingSpeed());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameObjectMessage getNetworkMessage(DataInputStream dis) {
        try {
            // First write ID
            int id = dis.readInt();
            // Then write Position
            // X
            float positionX = dis.readFloat();
            // Y
            float positionY = dis.readFloat();
            // Then we also need the size
            float sizeX = dis.readFloat();
            float sizeY = dis.readFloat();
            // We also need the speed
            float speedX = dis.readFloat();
            float speedY = dis.readFloat();
            // We also need the bounciness
            float impulseX = dis.readFloat();
            float impulseY = dis.readFloat();
            // We also need the
            float maxSpeed = dis.readFloat();
            PhysicsObject p = new SynchronizedGameObject(new Vector2f(positionX, positionY), new Vector2f(sizeX, sizeY));
            p.setCurrentSpeed(new Vector2f(speedX, speedY));
            p.setImpulse(new Vector2f(impulseX, impulseY));
            p.setMaxFallingSpeed(maxSpeed);
            return new GameObjectMessage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(GameObjectMessage objectMessage) {
        // TODO: Change the world
        World.getInstance().addObject(objectMessage.toPhysicsObject());
    }
}
