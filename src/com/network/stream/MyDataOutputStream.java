package com.network.stream;

import com.game.event.Event;
import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.GameObjectType;
import com.game.gameworld.Player;
import com.game.input.Command;
import com.game.input.Command.CommandType;
import com.game.input.MoveCommand;
import com.helper.Vector2f;
import com.network.common.MessageType;

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
        writeInt(gameObject.getID());
        writeVector2f(gameObject.getPosition());
        writeVector2f(gameObject.getSize());
    }

    public void writeVector2f(Vector2f vector2f) throws IOException {
        writeFloat(vector2f.getX());
        writeFloat(vector2f.getY());
    }

    public void writeCommand(Command c) throws IOException {
        writeByte(c.getCommandType().getID());
        writeInt(c.getPlayer().getID());
        if (c.getCommandType().getID() == CommandType.MOVE.getID()) {
            MoveCommand mC = (MoveCommand)c;
            writeBoolean(mC.getLeft());
            writeBoolean(mC.getMove());
        }
    }

    public void writeEvent(Event e) throws IOException {
        /*writeByte(MessageType.EVENT.getID());
        EventType eventType = eventMessage.getEvent().getEventType();
        dos.writeByte(eventType.getID());
        dos.writeGameObject(eventMessage.getEvent().getGameObject());
        if (eventType == EventType.ADD) {
            // TODO: Auslagern?
            // First write the Type of the EVENT
            GameObject object = event.getGameObject();
            GameObjectType type = event.getGameObject().getGameObjectType();
            // Write the ID
            dos.writeByte(type.getID());
            switch(type) {
                case PLAYER:
                    Player p = (Player)eventMessage.getEvent().getGameObject();
                    dos.writeUTF(p.getUsername());
                    break;
                case ITEM:
                    break;
            }

        }*/
    }

}
