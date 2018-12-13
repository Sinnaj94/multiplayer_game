package com.helper;

/**
 * Vector2 Float implementation
 */
public class Vector2f extends Vector2<Float, Vector2f> {
    public Vector2f(Float x, Float y) {
        super(x, y);
    }

    @Override
    public void addY(Float y) {
        setY(getY() + y);
    }

    @Override
    public void addX(Float x) {
        setX(getX() + x);
    }

    @Override
    public void add(Vector2f v) {
        addX(v.getX());
        addY(v.getY());
    }

    public void multiply(Float f) {
        this.multiply(new Vector2f(f, f));
    }

    @Override
    public void setVector(Vector2f v) {
        setX(v.getX());
        setY(v.getY());
    }

    @Override
    public void multiply(Vector2f v) {
        setX(getX() * v.getY());
        setY(getY() * v.getY());
    }

    public Vector2f distanceTo(Vector2f other) {
        return new Vector2f(Math.abs(getX() - other.getX()), Math.abs(getY() -other.getY()));
    }
}
