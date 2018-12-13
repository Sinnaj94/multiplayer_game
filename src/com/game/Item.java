package com.game;

import com.game.collectable.Collectable;
import com.game.gameworld.PhysicsObject;
import com.game.gameworld.Player;
import com.helper.Vector2f;

public class Item extends PhysicsObject implements Collectable {
    public Item(Vector2f position, Vector2f size) {
        super(position, size);
    }

    @Override
    public void give(Player p) {

    }
}
