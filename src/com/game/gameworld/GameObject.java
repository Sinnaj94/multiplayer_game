package com.game.gameworld;

import com.game.generics.Renderable;
import com.game.generics.Updateable;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;

// TODO: What is better? Vector2i or Vector2f?
public abstract class GameObject extends BoundingBox implements Renderable, Updateable {
    private static int ID;

    public int getMyID() {
        return myID;
    }

    private int myID;

    public GameObject() {
        super(new Vector2f(0f, 0f), new Vector2f(16f, 16f));

        myID = ID++;
        //System.out.println("Added Game Object with ID " + myID);
    }

    public GameObject(Vector2f position, Vector2f size) {
        super(position, size);
        myID = ID++;
    }

    public GameObject(Vector2f position, Vector2f size, int id) {
        super(position, size);
        myID = id;
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

    public GameObjectType getGameObjectType() {
        return GameObjectType.GAMEOBJECT;
    }
}
