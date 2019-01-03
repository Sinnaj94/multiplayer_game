package com.network.common;

import com.game.event.*;
import com.game.gameworld.GameObject;
import com.game.gameworld.GameObjectType;
import com.game.gameworld.Player;
import com.game.gameworld.World;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventMessageHandler implements NetworkMessageHandler<EventMessage> {
    private List<MyDataOutputStream> outputStreams;

    public EventMessageHandler() {
        outputStreams = new ArrayList<>();
    }

    @Override
    public void sendMessage(EventMessage eventMessage, MyDataOutputStream dos) {
        Event event = eventMessage.getEvent();
        try {
            dos.writeByte(MessageType.EVENT.getID());
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
                        dos.writeBoolean(((AddGameObjectEvent) eventMessage.getEvent()).isMainPlayer());
                        dos.writeUTF(p.getUsername());
                        break;
                    case ITEM:
                        break;
                }

            }
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO
    }

    public void addMyDataOutputStream(MyDataOutputStream myDataOutputStream) {
        outputStreams.add(myDataOutputStream);
    }

    @Override
    public EventMessage getNetworkMessage(MyDataInputStream dis) {
        try {
            EventType eventType = EventType.getEventType(dis.readByte());
            GameObject gameObject = dis.readGameObject();
            switch (eventType) {
                case ADD:
                    // TODO: not only player.
                    GameObjectType gameObjectType = GameObjectType.getMessageTypeByID(dis.readByte());
                    switch(gameObjectType) {
                        case PLAYER:
                            boolean isMainPlayer = dis.readBoolean();
                            String username = dis.readUTF();
                            Player player = new Player(gameObject.getPosition(), gameObject.getSize(), gameObject.getMyID());
                            player.setUsername(username);
                            return new EventMessage(new AddGameObjectEvent(player, isMainPlayer));

                    }
                    /*boolean isMainPlayer = dis.readBoolean();
                    return new EventMessage(new AddGameObjectEvent(new Player(gameObject.getPosition(), gameObject.getSize(), gameObject.getMyID()), isMainPlayer));*/
                case MOVE:
                    return new EventMessage(new MoveGameObjectEvent(gameObject));
                case REMOVE:
                    return new EventMessage(new RemoveGameObjectEvent(gameObject));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(EventMessage eventMessage) {
        World.getInstance().addEvent(eventMessage.getEvent());
    }
}
