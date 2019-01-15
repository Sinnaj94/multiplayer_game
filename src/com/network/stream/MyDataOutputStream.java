package com.network.stream;

import com.game.event.Event;
import com.game.event.EventType;
import com.game.event.gameobject.AddGameObjectEvent;
import com.game.event.gameobject.GameObjectEvent;
import com.game.event.gameobject.MoveGameObjectEvent;
import com.game.event.player.MoveEvent;
import com.game.event.player.PlayerEvent;
import com.game.gameworld.AIPlayer;
import com.game.gameworld.GameObject;
import com.game.event.player.Command;
import com.game.event.player.Command.CommandType;
import com.game.event.player.MoveCommand;
import com.game.gameworld.GameObjectType;
import com.game.gameworld.Player;
import com.helper.BoundingBox;
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
        writeInt(gameObject.getID());
        writeBoundingBox(gameObject.getBoundingBox());
    }

    public void writeVector2f(Vector2f vector2f) throws IOException {
        writeFloat(vector2f.getX());
        writeFloat(vector2f.getY());
    }

    public void writeBoundingBox(BoundingBox boundingBox) throws IOException {
        writeVector2f(boundingBox.getPosition());
        writeVector2f(boundingBox.getSize());
    }

    public void writeCommand(Command c) throws IOException {
        writeByte(c.getCommandType().getID());
        writeInt(c.getId());
        if (c.getCommandType().getID() == CommandType.MOVE.getID()) {
            MoveCommand mC = (MoveCommand)c;
            writeBoolean(mC.getLeft());
            writeBoolean(mC.getMove());
        }
    }

    public void writeEvent(Event event) throws IOException {
        EventType eventType = event.getEventType();

        // Writing the first byte so we know what it is.
        writeByte(eventType.getID());

        // Writing the ID concerning the GameObject
        writeInt(event.getID());
        // Game Object Event (Add Remove Update)

        // Writing the ID
        switch(eventType) {
            // Adding a GameObject
            case ADD:
                AddGameObjectEvent g = (AddGameObjectEvent) event;
                // Writing position as well as size
                writeBoundingBox(g.getGameObject().getBoundingBox());
                GameObjectType type = g.getGameObject().getGameObjectType();
                writeByte(type.getID());
                // Writing additional information.
                switch(type) {
                    case PLAYER:
                        // Writing additional attributes (username)
                        Player p = (Player)g.getGameObject();
                        // Username
                        writeUTF(p.getUsername());
                        break;
                    case AIPLAYER:
                        AIPlayer ai = (AIPlayer)g.getGameObject();
                        writeUTF(ai.getUsername());
                        break;
                }
                break;
            case MOVE:
                // Write the Position
                MoveGameObjectEvent a = (MoveGameObjectEvent)event;
                writeVector2f(a.getPosition());
                break;
            case REMOVE:
                // Only ID is need here.
                break;
            case PLAYERMOVE:
                MoveEvent pm = (MoveEvent)event;
                writeBoolean(pm.isLeft());
                writeBoolean(pm.isMove());
                break;
            case PLAYERJUMP:
                break;
        }
    }
}
