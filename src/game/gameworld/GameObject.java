package game.gameworld;

import game.generics.Collideable;
import game.generics.Placeable;
import game.generics.Renderable;
import game.generics.Updateable;
import helper.BoundingBox;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;

// TODO: What is better? Vector2i or Vector2f?
public abstract class GameObject extends BoundingBox implements Renderable, Updateable {
    private static int ID;
    private static int myID;

    public GameObject() {
        super(new Vector2f(0f, 0f), new Vector2f(16f, 16f));

        myID = ID++;
        //System.out.println("Added Game Object with ID " + myID);
    }

    public GameObject(Vector2f position, Vector2f size) {
        super(position, size);
        myID = ID++;
    }

    @Override
    public void update() {
        //TODO: Can be destroyed and collected
        return;
    }

    // Debug draw
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
