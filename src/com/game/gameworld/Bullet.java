package com.game.gameworld;

import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;

public class Bullet extends PhysicsObject {
    private int lifetime;
    private int maxLifetime;

    public Bullet(Vector2f position, Vector2f speed) {
        super(new BoundingBox(position, new Vector2f(5f, 5f)), -1);
        getAdvancedBoundingBox().setMargin(1);
        setMaxFallingSpeed(0f);
        maxLifetime = 60;
        lifetime = 0;
        this.accelerate(new Vector2f(10f * speed.getX(), 10f * speed.getY()));
    }

    public boolean dies() {
        //return lifetime > maxLifetime || getCollision().collides();
        return false;
    }

    @Override
    public void update() {
        super.update();
        this.lifetime++;
    }

    @Override
    public void paint(Graphics g) {
        if (World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            Rectangle r = toIntRectangle();
            g.fillRect(r.x, r.y, r.width, r.height);
        }
    }
}
