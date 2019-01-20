package com.game.gameworld.players;

import com.game.ai.AIState;
import com.game.gameworld.GameObjectType;
import com.game.tiles.ResourceSingleton;
import com.helper.BoundingBox;
import com.helper.Vector2f;

public class Ninja extends AIPlayer {
    public Ninja(String username) {
        super(username);
    }

    public Ninja(String username, Vector2f position) {
        super(username, position);
    }

    public Ninja(BoundingBox prototype, int id, String username) {
        super(prototype, id, username);
    }

    @Override
    public void buildAttributes() {
        super.buildAttributes();
        setAiState(AIState.ATTACKJUMP);
        setWalkingSpeed(2f);
        setReloadSpeed(100);
        setJumpAcceleration(-6f);
        setTilesetFactory(ResourceSingleton.getInstance().getNinjas());
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.NINJA;
    }
}
