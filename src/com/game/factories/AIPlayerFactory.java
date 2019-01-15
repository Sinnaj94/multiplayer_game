package com.game.factories;

import com.game.gameworld.AIPlayer;
import com.game.gameworld.Player;
import com.helper.Vector2f;

public class AIPlayerFactory extends PlayerFactory {
    @Override
    public AIPlayer spawn() {
        return spawn(new Vector2f(0f, 0f));
    }

    @Override
    public AIPlayer spawn(Vector2f position) {
        return new AIPlayer("enemy", position);
    }
}
