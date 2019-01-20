package com.game.gameworld.players;

import com.game.ai.AIState;
import com.game.gameworld.GameObjectType;
import com.game.tiles.ResourceSingleton;
import com.helper.BoundingBox;
import com.helper.Vector2f;

public class Zombie extends AIPlayer {
    public Zombie(String username) {
        super(username);
    }

    public Zombie(String username, Vector2f position) {
        super(username, position);
    }

    public Zombie(BoundingBox prototype, int id, String username) {
        super(prototype, id, username);
    }

    @Override
    public void buildAttributes() {
        super.buildAttributes();
        setAiState(AIState.FOLLOW);
        setWalkingSpeed(1.5f);
        setJumpAcceleration(-4f);
        setTilesetFactory(ResourceSingleton.getInstance().getZombies());
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.ZOMBIE;
    }
}
