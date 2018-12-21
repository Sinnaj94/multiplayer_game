package com.game.input;

import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.game.gameworld.World;

public interface Command {
    void execute(Player p);
    CommandType getCommandType();

    public enum CommandType {
        JUMP((byte)0), MOVE((byte)1);
        private byte b;

        CommandType(byte b) {
            this.b = b;
        }

        public byte getID() {
            return b;
        }

        public static CommandType getMessageTypeByByte(byte b) {
            for(CommandType m:CommandType.values()) {
                if(m.getID() == b) {
                    return m;
                }
            }
            return null;
        }
    }

}

