package com.game.gameworld;

public enum GameObjectType {
    GAMEOBJECT((byte)0), PHYSICSOBJECT((byte)1), PLAYER((byte)2), ITEM((byte)3), AIPLAYER((byte)4), BULLET((byte)5),
    ZOMBIE((byte)6), NINJA((byte)7);
    byte id;

    GameObjectType(byte id) {
        this.id = id;
    }

    public byte getID() {
        return id;
    }

    public static GameObjectType getMessageTypeByID(byte id) {
        for (GameObjectType m : GameObjectType.values()) {
            if (m.getID() == id) {
                return m;
            }
        }
        return null;
    }
}
