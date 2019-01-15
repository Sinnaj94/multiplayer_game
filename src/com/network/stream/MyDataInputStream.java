package com.network.stream;

import com.game.event.Event;
import com.game.event.EventType;
import com.game.event.gameobject.AddGameObjectEvent;
import com.game.event.gameobject.MoveGameObjectEvent;
import com.game.event.gameobject.RemoveGameObjectEvent;
import com.game.event.player.*;
import com.game.gameworld.*;
import com.game.event.player.Command.CommandType;
import com.helper.BoundingBox;
import com.helper.Vector2;
import com.helper.Vector2f;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDataInputStream extends DataInputStream {
    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public MyDataInputStream(InputStream in) {
        super(in);
    }

    public GameObject readGameObject() throws IOException {
        int id = readInt();
        Vector2f position = readVector2f();
        Vector2f size = readVector2f();
        return new SynchronizedGameObject(new BoundingBox(position, size), id);
    }

    public Vector2f readVector2f() throws IOException {
        float x = readFloat();
        float y = readFloat();
        return new Vector2f(x, y);
    }

    public BoundingBox readBoundingBox() throws IOException {
        Vector2f position = readVector2f();
        Vector2f size = readVector2f();
        return new BoundingBox(position, size);
    }

    public Command readCommand() throws IOException {
        byte b = readByte();
        CommandType c = CommandType.getMessageTypeByByte(b);
        int id = readInt();
        switch (c) {
            case JUMP:
                return new JumpCommand(id);
            case MOVE:
                boolean left = readBoolean();
                boolean move = readBoolean();
                MoveCommand m = new MoveCommand(id, left, move);
                return m;
            case SHOOT:
                Vector2f direction = readVector2f();
                return new ShootCommand(id, direction);
        }
        return null;
    }

    public Event readEvent() throws IOException {
        // Read the Eventtype
        EventType eventType = EventType.getEventType(readByte());

        // Reading the GameObject ID
        int gameObjectID = readInt();

        // GameObjectEvent
        switch(eventType) {
            case ADD:
                BoundingBox temp = readBoundingBox();
                GameObjectType objectType = GameObjectType.getMessageTypeByID(readByte());
                switch(objectType) {
                    case PLAYER:
                        String username = readUTF();
                        Player player = new Player(temp, gameObjectID, username);
                        player.setWalkingSpeed(readFloat());
                        player.setJumpAcceleration(readFloat());
                        return new AddGameObjectEvent(player);
                    case AIPLAYER:
                        String name = readUTF();
                        AIPlayer aiPlayer = new AIPlayer(temp, gameObjectID, name);
                        aiPlayer.setWalkingSpeed(readFloat());
                        aiPlayer.setJumpAcceleration(readFloat());
                        return new AddGameObjectEvent(aiPlayer);
                    case ITEM:
                        return new AddGameObjectEvent(new Item(temp, gameObjectID));
                    case BULLET:
                        Vector2f speed = readVector2f();
                        int playerID = readInt();
                        return new AddGameObjectEvent(new Bullet(temp, gameObjectID, speed, playerID));
                    default:
                        return new AddGameObjectEvent(new SynchronizedGameObject(temp, gameObjectID));
                }
            case MOVE:
                Vector2f position = readVector2f();
                return new MoveGameObjectEvent(gameObjectID, position);
            case REMOVE:
                return new RemoveGameObjectEvent(gameObjectID);
            case PLAYERJUMP:
                return new JumpEvent(gameObjectID);
            case PLAYERMOVE:
                boolean left = readBoolean();
                boolean right = readBoolean();
                return new MoveEvent(gameObjectID, left, right);
            case ITEMGIVE:
                int itemID = readInt();
                return new GiveItemEvent(gameObjectID, itemID);
            case HITPLAYER:
                return new HitPlayerEvent(gameObjectID);
            case KILLPLAYER:
                return new KillPlayerEvent(gameObjectID);
            case SHOOT:
                Vector2f direction = readVector2f();
                return new ShootEvent(gameObjectID, direction);
        }
        return null;
    }
}
