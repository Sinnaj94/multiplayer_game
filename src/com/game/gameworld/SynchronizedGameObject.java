package com.game.gameworld;

import com.helper.Vector2f;

public class SynchronizedGameObject extends GameObject {
    private Vector2f delta;
    private double timeDifference;
    private Vector2f distance;
    private double last;

    public SynchronizedGameObject(Vector2f position, Vector2f size, int id) {
        super(position, size, id);
        distance = new Vector2f(0f, 0f);
        delta = new Vector2f(0f, 0f);
        timeDifference = 0;
        last = System.currentTimeMillis();
    }

    public void deltaPosition(Vector2f delta) {
        timeDifference = System.currentTimeMillis() - last;
        this.delta.setVector(delta);
        last = System.currentTimeMillis();
    }

    @Override
    public void update() {
        setPosition(delta);
    }
}
