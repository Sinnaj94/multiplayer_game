package game.gameobjects;

import com.oracle.tools.packager.Log;
import game.generics.Movable;
import game.generics.Renderable;
import helper.Vector2;
import helper.Vector2f;
import helper.Vector2i;

// TODO: What is better? Vector2i or Vector2f?
public abstract class GameObject implements Movable<Vector2f>, Renderable {
    private Vector2f position;
    private Vector2i size;
    // Speed is a variable, which is multiplied with the position
    private float speed;
    private Vector2f delta;
    public GameObject() {
        this.speed = 1f;
        position = new Vector2f(0f, 0f);
        delta = new Vector2f(0f, 0f);
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
        this.delta.add(new Vector2f(delta.getX() * speed, delta.getY() * speed));
    }

    @Override
    public void update() {
        this.position.add(this.delta);
    }
}
