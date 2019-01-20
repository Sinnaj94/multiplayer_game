package com.game.factories;

import com.game.ai.AIState;
import com.game.gameworld.players.AIPlayer;
import com.helper.Vector2f;

import java.util.Random;

public class AIPlayerFactory extends PlayerFactory {
    public Random getR() {
        return r;
    }

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
        AIPlayer ai = new AIPlayer("ENEMY", position);
        ai.setAiState(AIState.FOLLOW);
        return ai;
    }
}
