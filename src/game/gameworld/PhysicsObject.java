package game.gameworld;

import helper.AdvancedBoundingBox;
import helper.Vector2f;

import java.awt.*;
import java.util.Random;

public abstract class PhysicsObject extends GameObject {
    private float friction;
    private final float GRAVITY = 0.089f;
    private float maxFallingSpeed;
    private float acceleration;
    private Vector2f impulse;
    private float maxRunningSpeed;
    private float jumpRequest;

    public Vector2f getCurrentSpeed() {
        return currentSpeed;
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
            float distance= advancedBoundingBox.getMargin() -
                            (advancedBoundingBox.getDown().createIntersection(
                             World.getInstance().getLevel().getBoundingBox())).getHeight();
            if(distance < this.currentSpeed.getY() && currentSpeed.getY() > 0) {
                // Save the impulse.
                this.impulse.setY((-this.currentSpeed.getY() * bounciness));
                this.currentSpeed.setY(0f);
                this.accelerate(impulse);
                touchesFloor = true;
            }
        }
    }

    public boolean getTouchesFloor() {
        return this.touchesFloor;
    }

    public void accelerate(Vector2f speed) {
        speed.multiply(friction);
        this.currentSpeed.add(speed);
    }

    public boolean touchesDown() {
        return advancedBoundingBox.getDown().intersects(World.getInstance().getLevel().getBoundingBox());
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
