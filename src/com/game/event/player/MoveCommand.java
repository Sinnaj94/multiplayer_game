package com.game.event.player;

import com.game.gameworld.World;

public class MoveCommand extends Command {
    private boolean left;
    private boolean move;
    public MoveCommand(int id, boolean left, boolean move) {
        super(id);
        this.left = left;
        this.move = move;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getMove() {
        return move;
    }


    @Override
    public void execute(World.Accessor accessor) {
        accessor.addEvent(new MoveEvent(getId(), left, move));
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MOVE;
    }
}
