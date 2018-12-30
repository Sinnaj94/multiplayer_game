package com.game.generics;

public interface Placeable<Number, Vector2> {
    public void setPosition(Vector2 position);

    public void setPosition(float x, float y);

    public Vector2 getPosition();

    public void setSize(Vector2 size);

    public Vector2 getSize();

    public void translate(Number x, Number y);
}
