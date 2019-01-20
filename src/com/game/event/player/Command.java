package com.game.event.player;

import com.game.gameworld.World;

/**
 * Command that can be sent from the client to the server
 */
public abstract class Command {
    public int getId() {
        return id;
    }

    int id;
    public Command(int id) {
        this.id = id;
    }

    /**
     * Execute the Command on the Current World
     * @param w the World
     */
    public abstract void execute(World.Accessor w);

    public abstract CommandType getCommandType();

    public enum CommandType {
        JUMP((byte) 0), MOVE((byte) 1), SHOOT((byte)2);
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

