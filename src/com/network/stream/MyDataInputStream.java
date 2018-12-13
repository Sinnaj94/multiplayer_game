package com.network.stream;

import com.game.event.Event;
import com.game.gameworld.GameObject;
import com.game.gameworld.SynchronizedGameObject;
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
        Vector2f position = new Vector2f(readFloat(), readFloat());
        Vector2f size = new Vector2f(readFloat(), readFloat());
        return new SynchronizedGameObject(position, size, id);
    }
}
