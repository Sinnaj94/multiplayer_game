package game.gameworld;

import helper.Vector2;
import helper.Vector2f;

public class SynchronizedGameObject extends GameObject {
    public SynchronizedGameObject(Vector2f position, Vector2f size) {
        super(position, size);
    }

    public SynchronizedGameObject(Vector2f position, Vector2f size, int id) {
        super(position, size, id);
    }
}
