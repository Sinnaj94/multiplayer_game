package com.network.stream;

import com.game.gameworld.GameObject;
import com.game.gameworld.SynchronizedGameObject;
import com.game.input.Command;
import com.game.input.Command.CommandType;
import com.game.input.JumpCommand;
import com.game.input.MoveCommand;
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
        return new SynchronizedGameObject(position, size, id);
    }

    public Vector2f readVector2f() throws IOException {
        float x = readFloat();
        float y = readFloat();
        return new Vector2f(x, y);
    }

    public Command readCommand() throws IOException {
        byte b = readByte();
        CommandType c = CommandType.getMessageTypeByByte(b);
        int id = readInt();
        switch (c) {
            case JUMP:
                return new JumpCommand(id);
            case MOVE:
                int direction = readInt();
                MoveCommand m = new MoveCommand(id);
                m.setDirection(direction);
                return m;
        }
        return null;
    }
}
