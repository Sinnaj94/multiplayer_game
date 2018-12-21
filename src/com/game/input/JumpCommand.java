package com.game.input;

import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.helper.Vector2f;

public class JumpCommand implements Command {
    @Override
    public void execute(Player p) {
        p.jump();
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.JUMP;
    }
}
