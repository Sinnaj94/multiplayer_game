package com.game.gameworld;

import com.helper.AdvancedBoundingBox;
import com.helper.Vector2f;

import java.awt.*;

public abstract class PhysicsObject extends GameObject {
    private float friction;
    private final float GRAVITY = 0.089f;
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

    private void applyGravity() {
        this.currentSpeed.addY(GRAVITY);
        if (this.currentSpeed.getY() > maxFallingSpeed) {
            this.currentSpeed.setY(maxFallingSpeed);
        }
        touchesFloor = false;
        if (touchesDown()) {
            touchesFloor = true;
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
            }

            /*
            System.out.println(distance);
            if(distance < this.currentSpeed.getY()) {
                // Save the impulse.
                if(this.currentSpeed.getY() < .05f) {
                    this.impulse.setY(0f);
                    this.accelerate(impulse);
                } else {
                    this.impulse.setY((-this.currentSpeed.getY() * bounciness));
                }
                this.currentSpeed.setY(0f);
                this.getPosition().addY(distance);
                touchesFloor = true;
            }*/
        }
    }

    private boolean gameObjectCollision() {
        for(GameObject g:World.getInstance().getDynamics()) {
            if(g != this) {
                if(advancedBoundingBox.getDown().intersects(g.getBoundingBox())) {
                    System.out.println("INTERSECTION");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getTouchesFloor() {
        return this.touchesFloor;
    }

    public void accelerate(Vector2f speed) {
        speed.multiply(friction);
        this.currentSpeed.add(speed);
    }

    public boolean touchesDown() {
        return advancedBoundingBox.getDown().intersects(World.getInstance().getCollideable().getBoundingBox());
    }

    @Override
    public void update() {
        applyGravity();
        getPosition().add(this.currentSpeed);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //advancedBoundingBox.getUp().paint(g);
        //advancedBoundingBox.getDown().paint(g);
    }
}