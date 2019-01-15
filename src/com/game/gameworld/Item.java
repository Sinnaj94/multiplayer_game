package com.game.gameworld;

import com.game.generics.Collectable;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;

public class Item extends PhysicsObject implements Collectable {
    public int getPlayerID() {
        return playerID;
    }

    int playerID;
    public boolean isGiven() {
        return given;
    }

    private boolean given;

    public Item() {
        this(new Vector2f(0f,0f));
    }

    public Item(Vector2f position) {
        this(new BoundingBox(position, new Vector2f(16f, 16f)));
    }

    public Item(BoundingBox prototype) {
        super(prototype);
        given = false;

    }

    public Item(BoundingBox prototype, int id) {
        super(prototype, id);
        given = false;
    }


    @Override
    public boolean canTake(Player p) {
        return p.canTake();
    }


    public void assign(Player p) {
        System.out.println("Assigned");
        given = true;
        playerID = p.getID();
    }


    @Override
    public void give(Player p) {
        p.addItem(this);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void paint(Graphics g) {
        if (World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            g.setColor(Color.MAGENTA);
            Rectangle r = toIntRectangle();
            //g.fillRect(toIntRectangle().x, toIntRectangle().y, toIntRectangle().width, toIntRectangle().height);
            g.fillOval(r.x, r.y, r.width, r.height);
        }
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.ITEM;
    }
}
