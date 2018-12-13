package com.network.common;

import com.game.event.Event;
import com.game.gameworld.GameObject;

public class EventMessage implements NetworkMessage {
    private Event event;

    public Event getEvent() {
        return event;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    private GameObject gameObject;

    public EventMessage(Event event, GameObject gameObject) {
        this.event = event;
        this.gameObject = gameObject;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.EVENT;
    }
}
