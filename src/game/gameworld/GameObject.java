package game.gameworld;

import game.generics.Collideable;
import game.generics.Placeable;
import game.generics.Renderable;
import game.generics.Updateable;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;

// TODO: What is better? Vector2i or Vector2f?
public abstract class GameObject implements Placeable<Vector2f>, Renderable, Collideable, Updateable {
    private static int ID;
    private static int myID;

    // Placeable
    private Vector2f position;
    private Vector2i size;

    public GameObject() {
        myID = ID++;
        //System.out.println("Added Game Object with ID " + myID);
        position = new Vector2f(0f, 0f);
        size = new Vector2i(16, 16);
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2f position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    @Override
    public Vector2i getSize() {
        return this.size;
    }

    @Override
    public void setSize(Vector2i size) {
        this.size = size;
    }

    // Return the bounding box
    @Override
    public Rectangle boundingBox() {
        return new Rectangle(Math.round(position.getX()), Math.round(position.getY()), size.getX(), size.getY());
    }

    // Basic collider classs
    @Override
    public boolean intersects(Collideable collideable) {
        return collideable.boundingBox().intersects(boundingBox());
    }

    @Override
    public void update() {
        //TODO: Can be destroyed and collected
        return;
    }
}
