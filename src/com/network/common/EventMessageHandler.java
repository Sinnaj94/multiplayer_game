package com.network.common;

import com.game.event.*;
import com.game.gameworld.GameObject;
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
            dos.writeByte(MessageType.EVENT.getByte());

            EventType eventType = eventMessage.getEvent().getEventType();
            dos.writeByte(eventType.getID());
            dos.writeGameObject(eventMessage.getEvent().getGameObject());
            if (eventType == EventType.ADD) {
                dos.writeBoolean(((AddGameObjectEvent) eventMessage.getEvent()).isMainPlayer());
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
                    boolean isMainPlayer = dis.readBoolean();
                    return new EventMessage(new AddGameObjectEvent(new Player(gameObject.getPosition(), gameObject.getSize(), gameObject.getMyID()), isMainPlayer));
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
