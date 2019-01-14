package com.game.factories;

import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.helper.Vector2f;

public class PlayerFactory extends GameObjectFactory {
    @Override
    public Player spawn() {
        return new Player("KI");
    }

    @Override
    public GameObject spawn(Vector2f position) {
        return new Player("KI", position);
    }
}
