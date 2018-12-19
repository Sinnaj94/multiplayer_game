package com.game.gameworld;

import com.game.generics.Updateable;
import com.helper.Vector2i;

public class Camera implements Updateable {
    private Vector2i position;
    private GameObject observable;
    private Vector2i size;
    private Vector2i innerTolerance;
    private Vector2i outerTolerance;
    private float maxSpeed;
    public Camera(Vector2i position, Vector2i size, GameObject observable) {
        this.position = position;
        this.observable = observable;
        this.size = size;
        this.innerTolerance = new Vector2i((int)Math.floor(size.getX() * .3), (int)Math.floor(size.getY() * .3));
        this.outerTolerance = new Vector2i((int)Math.floor(size.getX() * .1), (int)Math.floor(size.getY() * .1));
        maxSpeed = 10f;
    }





    public Vector2i getPosition() {
        return position;
    }

    public void setPosition(Vector2i position) {
        this.position = position;
    }

    @Override
    public void update() {
        int x = observable.toIntRectangle().x;
        int y = observable.toIntRectangle().y;
        int r = x + observable.toIntRectangle().width;
        int d = y + observable.toIntRectangle().height;
        int distanceLeft = x - position.getX();
        int distanceRight = position.getX() + size.getX() - r;
        int distanceUp = y - position.getY();
        int distanceDown = position.getY() + size.getY() - d;
        if(distanceDown < innerTolerance.getY()) {
            float factor = 1f - distanceDown / (float)innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addY((int)speed);
            //position.addY();
        }
        if(distanceUp < innerTolerance.getY()) {
            float factor = 1f - distanceUp / (float)innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addY(-(int)speed);
        }
        if(distanceLeft < innerTolerance.getX()) {
            float factor = 1f - distanceLeft / (float)innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addX(-(int)speed);
        }
        if(distanceRight < innerTolerance.getX()) {
            float factor = 1f - distanceRight / (float)innerTolerance.getY();
            float speed = factor * maxSpeed;
            position.addX((int)speed);
        }
        /*if(x < -position.getX() + innerTolerance.getX()) {
            position.addX(2);
            if(x < -position.getX() + outerTolerance.getX()) {
                position.addX(4);
                if(x < -position.getX()) {
                    position.addX(10);
                }
            }
        } else if(r > -position.getX() + size.getX() - innerTolerance.getX()) {
            position.addX(-2);
            if(r > -position.getX() + size.getX() - outerTolerance.getX()) {
                position.addX(-4);
                if(r > -position.getX() + size.getX()) {
                    position.addX(-10);
                }
            }
        }

        if(y < -position.getY() + innerTolerance.getY()) {
            position.addY(2);
            if(y < -position.getY() + innerTolerance.getY()) {
                position.addY(4);
                if(y < -position.getY()) {
                    position.addY(10);
                }
            }
        } else if(d > -position.getY() + size.getY() - innerTolerance.getY()) {
            position.addY(-2);
            if(d > -position.getY() + size.getY() - outerTolerance.getY()) {
                position.addY(-4);
                if(d > -position.getY() + size.getY()) {
                    position.addY(-10);
                }
            }
        }*/

    }
}
