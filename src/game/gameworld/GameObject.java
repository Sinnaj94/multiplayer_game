package game.gameworld;

import game.generics.Movable;
import game.generics.Renderable;
import game.input.Collideable;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;

// TODO: What is better? Vector2i or Vector2f?
public abstract class GameObject implements Movable<Vector2f>, Renderable, Collideable {
    final float GRAVITY = 0.089f;
    final float MAX_FALLING_SPEED = 5f;
    private float maxRunningSpeed = 2f;
    private Vector2f position;
    private Vector2i size;
    // Speed is a variable, which is multiplied with the position
    private Vector2f currentSpeed;
    private Vector2f forceRequest;
    private boolean physicsEnabled;
    private boolean collides;
    private static int ID;
    private static int myID;
    public GameObject() {
        myID = ID++;
        //System.out.println("Added Game Object with ID " + myID);
        this.maxRunningSpeed = 1f;
        position = new Vector2f(0f, 0f);
        currentSpeed = new Vector2f(0f, 0f);
        forceRequest = new Vector2f(0f, 0f);
        setPhysicsEnabled(true);
        setCollides(true);
    }

    public void setPhysicsEnabled(boolean physicsEnabled) {
        this.physicsEnabled = physicsEnabled;
    }

    public void printPhysicsEnabled() {
        System.out.println(this.physicsEnabled);
    }

    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    @Override
    public void setPosition(Vector2f position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public void setSize(Vector2i size) {
        this.size = size;
    }

    @Override
    public Vector2i getSize() {
        return this.size;
    }

    @Override
    public void move(Vector2f delta) {
        this.forceRequest.add(new Vector2f(delta.getX() * maxRunningSpeed, 0f));
    }

    private void applyGravity() {
        this.currentSpeed.setY(this.currentSpeed.getY() + GRAVITY);
        if(this.currentSpeed.getY() > MAX_FALLING_SPEED) {
            this.currentSpeed.setY(MAX_FALLING_SPEED);
        }
        if(collides(World.getInstance().getLevel())) {
            this.currentSpeed.setY(0f);
            float difference = (float)Math.abs(World.getInstance().getLevel().boundingBox().createIntersection(boundingBox()).getHeight());
            this.position.setY(this.position.getY() - difference);
        }
    }

    // Return the bounding box
    @Override
    public Rectangle boundingBox() {
        return new Rectangle(Math.round(position.getX()), Math.round(position.getY()), size.getX(), size.getY());
    }

    // Basic collider classs
    @Override
    public boolean collides(Collideable collideable) {
        return collideable.boundingBox().intersects(boundingBox());
    }

    @Override
    public void update() {
        if(physicsEnabled) {

            applyGravity();
            this.position.add(this.currentSpeed);
        }
    }
}
