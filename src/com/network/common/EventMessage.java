package com.network.common;

import com.game.event.Event;
import com.game.event.gameobject.GameObjectEvent;

public class EventMessage implements NetworkMessage {
    private Event event;

    public Event getEvent() {
        return event;
    }

    public EventMessage(Event gameObjectEvent) {
        this.event = gameObjectEvent;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.EVENT;
    }
}
