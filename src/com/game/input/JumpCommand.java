package com.game.input;

public class JumpCommand extends Command {
    public JumpCommand(int id) {
        super(id);
    }

    @Override
    public void execute() {
        getPlayer().jump();
    }


    @Override
    public CommandType getCommandType() {
        return CommandType.JUMP;
    }
}
