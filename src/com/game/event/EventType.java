package com.game.event;

public enum EventType {
    ADD((byte) 0), MOVE((byte) 1), REMOVE((byte) 2);
    private byte id;

    EventType(byte id) {
        this.id = id;
    }

    public byte getID() {
        return this.id;
    }

    public static EventType getEventType(byte id) {
        for (EventType e : EventType.values()) {
            if (e.getID() == id) {
                return e;
            }
        }
        return null;
    }
}
