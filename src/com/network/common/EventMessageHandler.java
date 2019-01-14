package com.network.common;

import com.game.event.*;
import com.game.event.gameobject.AddGameObjectEvent;
import com.game.event.gameobject.GameObjectEvent;
import com.game.event.gameobject.MoveGameObjectEvent;
import com.game.event.gameobject.RemoveGameObjectEvent;
import com.game.event.player.PlayerEvent;
import com.game.gameworld.*;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventMessageHandler implements NetworkMessageHandler<EventMessage> {
    private World.Accessor accessor;

    public EventMessageHandler() {
        accessor = World.getInstance().getAccessor();
    }

    @Override
    public void sendMessage(EventMessage eventMessage, MyDataOutputStream dos) {
        try {
            dos.writeByte(MessageType.EVENT.getID());
            dos.writeEvent(eventMessage.getEvent());

        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO
    }

    @Override
    public EventMessage getNetworkMessage(MyDataInputStream dis) {
        try {
            Event e = dis.readEvent();
            return new EventMessage(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handle(EventMessage eventMessage) {
        if(eventMessage.getEvent()!=null) {
            accessor.addEvent(eventMessage.getEvent());
        }
    }
}
