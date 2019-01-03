package com.game.input;

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
    public void execute() {
        getPlayer().move(left, move);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MOVE;
    }
}
