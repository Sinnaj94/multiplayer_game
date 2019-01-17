package com.game.factories;

import com.game.gameworld.AIPlayer;
import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.game.tiles.PlayerTilesetFactory;
import com.helper.Vector2f;

public class PlayerFactory extends GameObjectFactory {
    @Override
    public Player spawn() {
        return spawn(new Vector2f(0f, 0f));
    }

    @Override
    public Player spawn(Vector2f position) {
        return new Player("player", position);
    }
}
