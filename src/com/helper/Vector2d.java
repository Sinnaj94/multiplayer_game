package com.helper;

public class Vector2d extends Vector2<Double, Vector2d> {
    public Vector2d(Double x, Double y) {
        super(x, y);
    }

    @Override
    public void addY(Double y) {
        setY(getY() + y);
    }

    @Override
    public void addX(Double x) {
        setX(getX() + x);
    }

    @Override
    public void add(Vector2d v) {
        addX(v.getX());
        addY(v.getY());
    }

    @Override
    public void setVector(Vector2d v) {
        setX(v.getX());
        setY(v.getY());
    }

    @Override
    public Vector2d multiply(Vector2d v) {
        setX(getX() * v.getY());
        setY(getY() * v.getY());
        return this;
    }
}
