package com.game.gameworld;

import com.helper.BoundingBox;
import com.helper.Vector2f;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class Bullet extends PhysicsObject {
    public int getPlayerID() {
        return playerID;
    }

    private int playerID;
    public Vector2f getInitialSpeed() {
        return initialSpeed;
    }

    private Vector2f initialSpeed;
    public Bullet(Vector2f position, Vector2f speed, int playerID) {
        super(new BoundingBox(position.getX(), position.getY(), 4, 4));
        this.playerID = playerID;
        fire(speed);
    }

    public Bullet(BoundingBox prototype, int id, Vector2f speed, int playerID) {
        super(prototype, id);
        this.playerID = playerID;
        fire(speed);
    }

    private void fire(Vector2f speed) {
        initialSpeed = speed;
        accelerate(speed);
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.BULLET;
    }

    @Override
    public void paint(Graphics g) {
        Rectangle r = toIntRectangle();
        g.setColor(Color.orange);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(Color.white);
        g.drawOval(r.x, r.y, r.width, r.height);
    }
}
