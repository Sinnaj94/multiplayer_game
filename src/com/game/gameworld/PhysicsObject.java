package com.game.gameworld;

import com.helper.AdvancedBoundingBox;
import com.helper.Vector2f;

import java.awt.*;

public abstract class PhysicsObject extends GameObject {
    private float friction;
    private final float GRAVITY = 0.089f;
    private World w;

    public Collision getCollision() {
        return collision;
    }

    private Collision collision;
    public void setMaxFallingSpeed(float maxFallingSpeed) {
        this.maxFallingSpeed = maxFallingSpeed;
    }

    public float getMaxFallingSpeed() {
        return maxFallingSpeed;
    }

    public Vector2f getImpulse() {
        return impulse;
    }

    public void setImpulse(Vector2f impulse) {
        this.impulse = impulse;
    }

    private float maxFallingSpeed;
    private float acceleration;
    private Vector2f impulse;
    private float maxRunningSpeed;
    private float jumpRequest;

    public Vector2f getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Vector2f currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    // Speed is a variable, which is multiplied with the position
    private Vector2f currentSpeed;
    private Vector2f constantForceRequest;
    private AdvancedBoundingBox advancedBoundingBox;
    private float bounciness;
    private boolean touchesFloor;

    public void setConstantForceRequest(Vector2f constantForceRequest) {
        this.constantForceRequest = constantForceRequest;
    }

    public PhysicsObject(Vector2f position, Vector2f size) {
        super(position, size);
        advancedBoundingBox = new AdvancedBoundingBox(getBoundingBox());
        maxRunningSpeed = 2f;
        acceleration = .01f;
        currentSpeed = new Vector2f(0f, 0f);
        constantForceRequest = new Vector2f(0f, 0f);
        jumpRequest = 0f;
        bounciness = 0.2f;
        maxFallingSpeed = 5f;
        friction = 0.9f;
        impulse = new Vector2f(0f, 0f);
        w = World.getInstance();
        collision = new Collision();
    }

    public PhysicsObject(Vector2f position, Vector2f size, int id) {
        super(position, size, id);
        advancedBoundingBox = new AdvancedBoundingBox(getBoundingBox());
        maxRunningSpeed = 2f;
        acceleration = .01f;
        currentSpeed = new Vector2f(0f, 0f);
        constantForceRequest = new Vector2f(0f, 0f);
        jumpRequest = 0f;
        bounciness = 0.2f;
        maxFallingSpeed = 5f;
        friction = 0.9f;
        impulse = new Vector2f(0f, 0f);
        w = World.getInstance();
        collision = new Collision();
    }

    public void setBounciness(float bounciness) {
        this.bounciness = bounciness;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxRunningSpeed(float maxRunningSpeed) {
        this.maxRunningSpeed = maxRunningSpeed;
    }

    public void calculateGravity() {
        this.currentSpeed.addY(GRAVITY);
        if (this.currentSpeed.getY() > maxFallingSpeed && maxFallingSpeed != 0) {
            this.currentSpeed.setY(maxFallingSpeed);
        }
            /*touchesFloor = true;
            if(currentSpeed.getY() > 0f) {
                // TODO: machen
                float distance = advancedBoundingBox.getMargin() -
                        (advancedBoundingBox.getDown().createIntersection(
                                World.getInstance().getLevel().getBoundingBox())).getHeight();
                // Das GameObject kommt auf dem Boden auf
                if(distance < currentSpeed.getY() && currentSpeed.getY() > 0) {
                    getPosition().addY(distance);
                    float lastSpeed = currentSpeed.getY();
                    currentSpeed.setY(0f);
                    if(lastSpeed > .05f) {
                        this.impulse.setY( -lastSpeed * bounciness);
                        accelerate(impulse);
                    }
                }
            }*/

    }

    public void updateCollision() {
        collision.setLeft(touchesLeft());
        collision.setRight(touchesRight());
        collision.setUp(touchesUp());
        collision.setDown(touchesDown());
    }

    public void applySpeed() {
        if (currentSpeed.getX() > 0) {
            if (collision.isRight()) {
                getCurrentSpeed().setX(0f);
            }
        } else if (currentSpeed.getX() < 0) {
            if (collision.isLeft()) {
                getCurrentSpeed().setX(0f);
            }
        }
        if (currentSpeed.getY() < 0) {
            if (collision.isUp()) {
                getCurrentSpeed().setY(0f);
            }
        } else if (currentSpeed.getY() > 0) {
            if (collision.isDown()) {
                getCurrentSpeed().setY(0f);
            }
        }
        translate(currentSpeed.getX(), currentSpeed.getY());
    }

    public void move(Vector2f direction) {
        if(direction.getX() > 0) {
            if(!collision.isRight()) {
                translate(direction.getX(), 0f);
            }
        } else if(direction.getX() < 0) {
            if(!collision.isLeft()) {
                translate(direction.getX(), 0f);
            }
        }
    }

    public void accelerate(Vector2f speed) {
        getCurrentSpeed().add(speed);
    }

    private boolean touchesDown() {
        return w.getCollideable().intersects(advancedBoundingBox.getDown());
    }

    private boolean touchesUp() {
        return w.getCollideable().intersects(advancedBoundingBox.getUp());
    }

    private boolean touchesRight() {
        return w.getCollideable().intersects(advancedBoundingBox.getRight());
    }

    private boolean touchesLeft() {
        return w.getCollideable().intersects(advancedBoundingBox.getLeft());
    }


    @Override
    public void update() {
        calculateGravity();
        applySpeed();
        updateCollision();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        advancedBoundingBox.getUp().paint(g);
        advancedBoundingBox.getDown().paint(g);
        advancedBoundingBox.getRight().paint(g);
        advancedBoundingBox.getLeft().paint(g);
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.PHYSICSOBJECT;
    }
}
