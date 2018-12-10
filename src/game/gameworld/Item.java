package game.gameworld;

import game.collectable.Collectable;
import helper.Vector2f;

import java.awt.*;

public class Item extends PhysicsObject implements Collectable {
    public Item(Vector2f position, Vector2f size) {
        super(position, size);
    }

    public Item(Vector2f position) {
        super(position, new Vector2f(16f, 16f));
    }

    @Override
    public void give(Player p) {
        if(p.addItem(this)) {
            World.getInstance().removeObject(this);
        }
    }

    @Override
    public void update() {
        super.update();
        updateTouchedItems();
    }

    private void updateTouchedItems() {
        for(PhysicsObject p:World.getInstance().getPlayers()) {
            if(p!=this && p instanceof Player) {
                if(p.intersects(getBoundingBox())) {
                    give((Player)p);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if(World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            g.drawRect(toIntRectangle().x, toIntRectangle().y, toIntRectangle().width, toIntRectangle().height);
        }
    }
}