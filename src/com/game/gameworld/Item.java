package com.game.gameworld;

import com.game.collectable.Collectable;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;

public class Item extends PhysicsObject implements Collectable {
    public Item(Vector2f position) {
        this(new BoundingBox(position, new Vector2f(16f, 16f)));
    }

    public Item(BoundingBox prototype) {
        super(prototype);
    }

    public Item(BoundingBox prototype, int id) {
        super(prototype, id);
    }



    @Override
    public void give(Player p) {
        /*if (p.addItem(this)) {
            World.getInstance().removeObject(getID());
        }*/
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
            g.fillRect(toIntRectangle().x, toIntRectangle().y, toIntRectangle().width, toIntRectangle().height);
        }
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.ITEM;
    }
}
