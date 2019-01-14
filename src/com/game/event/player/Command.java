package com.game.event.player;

import com.game.gameworld.Player;
import com.game.gameworld.World;

public abstract class Command {
    public int getId() {
        return id;
    }

    int id;
    public Command(int id) {
        this.id = id;
    }

    // TODO: Auslagern
    public abstract void execute(World.Accessor w);

    public abstract CommandType getCommandType();

    public enum CommandType {
        JUMP((byte) 0), MOVE((byte) 1);
        private byte b;

        CommandType(byte b) {
            this.b = b;
        }

        public byte getID() {
            return b;
        }

        public static CommandType getMessageTypeByByte(byte b) {
            for (CommandType m : CommandType.values()) {
                if (m.getID() == b) {
                    return m;
                }
            }
            return null;
        }
    }

}

