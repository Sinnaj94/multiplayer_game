package game.gameworld;

import game.generics.Collideable;
import helper.Vector2f;

import java.awt.*;

public abstract class PhysicsObject extends GameObject {
    private static final float FRICTION = .99f;
    private final float GRAVITY = 0.089f;
    private final float MAX_FALLING_SPEED = 5f;
    private float acceleration;
    private float maxRunningSpeed;
    private float jumpRequest;
    // Speed is a variable, which is multiplied with the position
    private Vector2f currentSpeed;
    private Vector2f constantForceRequest;
    private boolean intersects;

    public void setConstantForceRequest(Vector2f constantForceRequest) {
        this.constantForceRequest = constantForceRequest;
    }

    public PhysicsObject(Vector2f position) {
        position = new Vector2f(0f, 0f);
        maxRunningSpeed = 2f;
        acceleration = .01f;
        currentSpeed = new Vector2f(0f, 0f);
        constantForceRequest = new Vector2f(0f, 0f);
        jumpRequest = 0f;
        intersects = false;
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
        if (intersects(World.getInstance().getLevel())) {
            this.currentSpeed.setY(0f);
            System.out.println(this.currentSpeed.toString());
            double difference = World.getInstance().getLevel().boundingBox().createIntersection(boundingBox()).getHeight();
            getPosition().setY((float) Math.round(getPosition().getY() - difference));
            return;
        }
        this.currentSpeed.addY(GRAVITY);
        if (this.currentSpeed.getY() > MAX_FALLING_SPEED) {
            this.currentSpeed.setY(MAX_FALLING_SPEED);
        }
    }

    public void accelerate() {
        this.currentSpeed.addX(acceleration * constantForceRequest.getX());
        this.currentSpeed.addY(acceleration * constantForceRequest.getY());
        if (this.currentSpeed.getX() > maxRunningSpeed) {
            this.currentSpeed.setX(maxRunningSpeed);
        }
    }

    @Override
    public void update() {
        applyGravity();
        accelerate();
        getPosition().add(this.currentSpeed);
    }

    public class OuterBondings implements Collideable {
        private int tolerance;
        private Rectangle big;
        private SpecificBonding up;
        private SpecificBonding down;
        private SpecificBonding left;
        private SpecificBonding right;

        public OuterBondings(Rectangle r) {
            tolerance = 5;
            big = new Rectangle(r.x - tolerance, r.y -tolerance, r.width - tolerance * 2, r.height - tolerance * 2);
        }

        @Override
        public boolean intersects(Collideable collideable) {
            return boundingBox().intersects(collideable.boundingBox());
        }

        @Override
        public Rectangle boundingBox() {
            return big;
        }

        public class SpecificBonding implements Collideable {
            public SpecificBonding(Rectangle r) {

            }

            @Override
            public boolean intersects(Collideable collideable) {
                return false;
            }

            @Override
            public Rectangle boundingBox() {
                return null;
            }
        }
    }
}
