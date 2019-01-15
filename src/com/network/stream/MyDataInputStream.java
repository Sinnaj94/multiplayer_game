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
                        return new AddGameObjectEvent(new Player(temp, gameObjectID, username));
                    case AIPLAYER:
                        String name = readUTF();
                        return new AddGameObjectEvent(new AIPlayer(temp, gameObjectID, name));
                    case ITEM:
                        return new AddGameObjectEvent(new Item(temp, gameObjectID));
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
        }
        return null;
    }
}
