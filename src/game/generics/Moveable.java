package game.generics;

import helper.Vector2F;

public abstract class Moveable {
    private float delta;
    private Vector2F position;

    public Moveable() {
        this.position = new Vector2F(0, 0);
    }

    public Vector2F getPosition() {
        return this.position;
    }

    public void move(Vector2F delta) {
        position.add(delta);
    }
}
