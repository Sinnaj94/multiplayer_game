package com.network.stream;

import com.game.gameworld.GameObject;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyDataOutputStream extends DataOutputStream {
    /**
     * Creates a new data output stream to write data to the specified
     * underlying output stream. The counter <code>written</code> is
     * set to zero.
     *
     * @param out the underlying output stream, to be saved for later
     *            use.
     * @see FilterOutputStream#out
     */
    public MyDataOutputStream(OutputStream out) {
        super(out);
    }

    public void writeGameObject(GameObject gameObject) throws IOException {
        writeInt(gameObject.getMyID());
        writeFloat(gameObject.getPosition().getX());
        writeFloat(gameObject.getPosition().getY());
        writeFloat(gameObject.getSize().getX());
        writeFloat(gameObject.getSize().getY());
        flush();
    }

}
