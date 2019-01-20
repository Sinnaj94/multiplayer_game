package com.game.factories;

import com.game.ai.AIState;
import com.game.gameworld.players.AIPlayer;
import com.game.gameworld.players.Ninja;
import com.game.tiles.ResourceSingleton;
import com.helper.Vector2f;

public class NinjaFactory extends AIPlayerFactory {
    @Override
    public AIPlayer spawn(Vector2f position) {
        return new Ninja("ninja", position);
    }
}
