package game;

import game.collectable.Collectable;
import game.gameworld.PhysicsObject;
import game.gameworld.Player;
import helper.Vector2f;

public class Item extends PhysicsObject implements Collectable {
    public Item(Vector2f position, Vector2f size) {
        super(position, size);
    }

    @Override
    public void give(Player p) {

    }
}
