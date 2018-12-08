package game.gameworld;

import helper.Vector2f;

public abstract class PhysicsObject extends GameObject {
    private final float GRAVITY = 0.089f;
    private final float MAX_FALLING_SPEED = 5f;
    private float acceleration;
    private float maxRunningSpeed;
    private float jumpRequest;
    // Speed is a variable, which is multiplied with the position
    private Vector2f currentSpeed;
    private Vector2f constantForceRequest;

    public PhysicsObject() {
        maxRunningSpeed = 2f;
        acceleration = .01f;
        currentSpeed = new Vector2f(0f, 0f);
        constantForceRequest = new Vector2f(0f, 0f);
        jumpRequest = 0f;
    }

    public void move(Vector2f delta) {
        this.constantForceRequest.add(new Vector2f(delta.getX() * maxRunningSpeed, 0f));
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxRunningSpeed(float maxRunningSpeed) {
        this.maxRunningSpeed = maxRunningSpeed;
    }

    private void applyGravity() {
        this.currentSpeed.addY(GRAVITY);
        if (this.currentSpeed.getY() > MAX_FALLING_SPEED) {
            this.currentSpeed.setY(MAX_FALLING_SPEED);
        }
        if (intersects(World.getInstance().getLevel())) {
            this.currentSpeed.setY(0f);
            double difference = World.getInstance().getLevel().boundingBox().createIntersection(boundingBox()).getHeight();
            getPosition().setY((float) Math.round(getPosition().getY() - difference));
        }
    }

    private void applyConstantForce() {
        this.currentSpeed.addX(acceleration * constantForceRequest.getX());
        this.currentSpeed.addY(acceleration * constantForceRequest.getY());
        if (this.currentSpeed.getX() > maxRunningSpeed) {
            this.currentSpeed.setX(maxRunningSpeed);
        }
    }

    @Override
    public void update() {
        applyGravity();
        applyConstantForce();
        getPosition().add(this.currentSpeed);
    }
}
