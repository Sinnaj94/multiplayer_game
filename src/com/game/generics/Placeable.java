package com.game.generics;

public interface Placeable<Vector2> {
    public void setPosition(Vector2 position);
    public Vector2 getPosition();
    public void setSize(Vector2 size);
    public Vector2 getSize();
}
