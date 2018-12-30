package com.game.input;

public class MoveCommand extends Command {
    private int direction;

    public MoveCommand(int id) {
        super(id);
    }

    public void setDirection(int direction) {
        if (direction < 0) {
            this.direction = -1;
        } else if (direction > 0) {
            this.direction = 1;
        } else {
            this.direction = 0;
        }
    }

    public int getDirection() {
        return direction;
    }


    @Override
    public void execute() {
        getPlayer().move(direction);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MOVE;
    }
}
