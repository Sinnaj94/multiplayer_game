package com.game.input;

import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.helper.Vector2f;

public class JumpCommand extends Command {
    public JumpCommand(Player player) {
        super(player);
    }

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
