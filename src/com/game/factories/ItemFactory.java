package com.game.factories;

import com.game.gameworld.GameObject;
import com.game.gameworld.Item;
import com.helper.Vector2f;

public class ItemFactory extends GameObjectFactory {
    @Override
    public GameObject spawn() {
        return new Item();
    }

    @Override
    public GameObject spawn(Vector2f position) {
        return new Item(position);
    }
}
