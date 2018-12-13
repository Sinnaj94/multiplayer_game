package com.network.common;

import com.game.event.AddGameObjectEvent;
import com.game.event.Event;
import com.game.event.MoveGameObjectEvent;
import com.game.event.RemoveGameObjectEvent;
import com.network.stream.MyDataInputStream;
import com.network.stream.MyDataOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EventMessageHandler implements NetworkMessageHandler<EventMessage> {
    @Override
    public void sendMessage(EventMessage eventMessage, MyDataOutputStream dos) {
        Event event = eventMessage.getEvent();
        try {
            // TODO: Pack das mal in ein enum
            dos.write(MessageType.EVENT.getByte());

            dos.writeGameObject(eventMessage.getGameObject());
        } catch(IOException e) {
            e.printStackTrace();
        }

        // TODO
    }

    @Override
    public EventMessage getNetworkMessage(MyDataInputStream dis) {
        return null;
    }

    @Override
    public void handle(EventMessage eventMessage) {

    }
}
