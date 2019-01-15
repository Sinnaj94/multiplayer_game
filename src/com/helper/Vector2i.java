package com.helper;

/**
 * Vector2 Integer implementation
 */
public class Vector2i extends Vector2<Integer, Vector2i> {

    public Vector2i(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public void addY(Integer y) {
        setY(getY() + y);
    }

    @Override
    public void addX(Integer x) {
        setX(getX() + x);
    }

    @Override
    public void add(Vector2i v) {
        addX(v.getX());
        addY(v.getY());
    }

    @Override
    public Vector2i multiply(Vector2i v) {
        setX(getX() * v.getX());
        setY(getY() * v.getY());
        return this;
    }

    @Override
    public void setVector(Vector2i v) {
        setX(v.getX());
        setY(v.getY());
    }
}
