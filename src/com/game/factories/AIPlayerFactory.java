package com.game.factories;

import com.game.ai.AIState;
import com.game.gameworld.AIPlayer;
import com.game.gameworld.Player;
import com.game.tiles.PlayerTilesetFactory;
import com.game.tiles.TilesetFactory;
import com.helper.Vector2f;

import java.util.Random;

public class AIPlayerFactory extends PlayerFactory {
    private Random r;
    public AIPlayerFactory() {
        r = new Random();
    }

    @Override
    public AIPlayer spawn() {
        return spawn(new Vector2f(0f, 0f));
    }

    @Override
    public AIPlayer spawn(Vector2f position) {
        AIPlayer ai = new AIPlayer("", position);
        ai.setWalkingSpeed(r.nextFloat() * 1 + 2);
        ai.setJumpAcceleration(-(r.nextFloat() * 1 + 1));
        ai.setAiState(AIState.IDLE);
        return ai;
    }
}
