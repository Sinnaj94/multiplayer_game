package com.network.common;

import com.game.event.Event;
import com.game.gameworld.GameObject;

public class EventMessage implements NetworkMessage {
    private Event event;

    public Event getEvent() {
        return event;
    }

    public EventMessage(Event event) {
        this.event = event;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.EVENT;
    }
}
