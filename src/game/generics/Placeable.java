package game.generics;

import helper.BoundingBox;
import helper.Vector2f;
import helper.Vector2i;

public interface Placeable<Vector2> {
    public void setPosition(Vector2 position);
    public Vector2 getPosition();
    public void setSize(Vector2 size);
    public Vector2 getSize();
}
