package com.game.gameworld;

import com.helper.Vector2i;

public class Camera {
    private Vector2i position;
    public void setSize(Vector2i size) {
        this.size = size;
    }

    private Vector2i size;
    private Vector2i innerTolerance;
    private Vector2i outerTolerance;
    private float maxSpeed;

    public Camera(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
        this.innerTolerance = new Vector2i((int) Math.floor(size.getX() * .3), (int) Math.floor(size.getY() * .3));
        this.outerTolerance = new Vector2i((int) Math.floor(size.getX() * .1), (int) Math.floor(size.getY() * .1));
        maxSpeed = 10f;
    }

    public Camera(Vector2i size) {
        this(new Vector2i(0, 0), size);
    }


    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    public void observe(GameObject observable) {
        int x = observable.toIntRectangle().x;
        int y = observable.toIntRectangle().y;
        int r = x + observable.toIntRectangle().width;
        int d = y + observable.toIntRectangle().height;
        int distanceLeft = x - position.getX();
        int distanceRight = position.getX() + size.getX() - r;
        int distanceUp = y - position.getY();
        int distanceDown = position.getY() + size.getY() - d;
        if (distanceDown < innerTolerance.getY()) {
            float factor = 1f - distanceDown / (float) innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addY((int) speed);
            //position.addY();
        }
        if (distanceUp < innerTolerance.getY()) {
            float factor = 1f - distanceUp / (float) innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addY(-(int) speed);
        }
        if (distanceLeft < innerTolerance.getX()) {
            float factor = 1f - distanceLeft / (float) innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addX(-(int) speed);
        }
        if (distanceRight < innerTolerance.getX()) {
            float factor = 1f - distanceRight / (float) innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addX((int) speed);
        }
    }
}
