package game.generics;

import helper.Vector2f;

public interface Movable<Vector2> {
    public void setPosition(Vector2 position);
    public Vector2 getPosition();
    public void move(Vector2 delta);
}
