package com.network.stream;

import com.game.gameworld.GameObject;
import com.game.input.Command;
import com.game.input.Command.CommandType;
import com.game.input.MoveCommand;
import com.helper.Vector2f;

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
        writeVector2f(gameObject.getPosition());
        writeVector2f(gameObject.getSize());
        flush();
    }

    public void writeVector2f(Vector2f vector2f) throws IOException {
        writeFloat(vector2f.getX());
        writeFloat(vector2f.getY());
    }

    public void writeCommand(Command c) throws IOException {
        writeByte(c.getCommandType().getID());
        writeInt(c.getPlayer().getMyID());
        //writeInt(c.getPlayer().getMyID());
        if (c.getCommandType().getID() == CommandType.MOVE.getID()) {
            writeInt(((MoveCommand) c).getDirection());
        }
    }

}
