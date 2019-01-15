package com.game.event.player;

import com.game.gameworld.World;

/**
 * JumpCommand
 */
public class JumpCommand extends Command {
    public JumpCommand(int id) {
        super(id);
    }

    @Override
    public void execute(World.Accessor accessor) {
        accessor.addEvent(new JumpEvent(getId()));
    }


    @Override
    public CommandType getCommandType() {
        return CommandType.JUMP;
    }
}
