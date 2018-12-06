package game.generics;

import helper.Vector2f;
import helper.Vector2i;

public interface Movable<Vector2> {
    public void setPosition(Vector2 position);
    public Vector2 getPosition();
    public void setSize(Vector2i size);
    public Vector2i getSize();
    public void move(Vector2 delta);
    public void update();
}
