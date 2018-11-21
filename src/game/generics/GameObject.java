package game.generics;

import helper.Vector2f;
import helper.Vector2i;

// TODO: What is better? Vector2i or Vector2f?
public abstract class GameObject implements Movable<Vector2f>, Renderable {
    private Vector2f position;

    public GameObject() {
        position = new Vector2f(0f, 0f);
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
    public void move(Vector2f delta) {
        this.position.add(delta);
    }
}
