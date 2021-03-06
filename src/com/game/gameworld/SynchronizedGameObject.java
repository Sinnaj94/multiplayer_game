package com.game.gameworld;

import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;

public class SynchronizedGameObject extends GameObject {
    private Vector2f delta;
    private double timeDifference;
    private Vector2f lastPosition;
    private Vector2f distance;
    private double last;
    private final int FACTOR = 40;
    private double averageRefresh;

    public SynchronizedGameObject(BoundingBox prototype, int id) {
        super(prototype, id);
        lastPosition = new Vector2f(getPosition().getX(), getPosition().getY());
        distance = new Vector2f(0f, 0f);
        delta = new Vector2f(0f, 0f);
        timeDifference = 0;
        averageRefresh = 0;
        last = System.currentTimeMillis();
    }

    public void deltaPosition(Vector2f delta) {
        setPosition(this.delta.getX(), this.delta.getY());
        distance = new Vector2f(delta.getX() - getPosition().getX(), delta.getY() - getPosition().getY());
        timeDifference = System.currentTimeMillis() - last;
        averageRefresh = (averageRefresh + timeDifference) / 2;
        this.delta = delta;
        last = System.currentTimeMillis();
    }

    @Override
    public void update() {
        // TODO
        setPosition(this.getPosition().getX() + (float) (distance.getX() * FACTOR / averageRefresh), this.getPosition().getY() + (float) (distance.getY() * FACTOR / averageRefresh));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.green);
        g.drawLine(Math.round(getPosition().getX()), Math.round(getPosition().getY()), Math.round(getPosition().getX() + distance.getX()), Math.round(getPosition().getY() + distance.getY()));
    }
}
