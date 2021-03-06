package com.game.event;

/**
 * Event Type - used for synchronization with clients
 */
public enum EventType {
    ADD((byte) 0), MOVE((byte) 1), REMOVE((byte) 2), PLAYERJUMP((byte) 3), PLAYERMOVE((byte)4), ITEMGIVE((byte)5),
    HITPLAYER((byte)6), KILLPLAYER((byte)7), SHOOT((byte)8);

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
