package com.game.input;

import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.game.gameworld.World;

public abstract class Command {
    private Player player;
    private int id;

    public Command(Player player) {
        this.player = player;
    }

    public Command(int id) {
        this.id = id;
        GameObject temp = World.getInstance().getObject(id);
        if(temp instanceof Player) {
            player = (Player)temp;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void execute();

    public abstract CommandType getCommandType();

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

