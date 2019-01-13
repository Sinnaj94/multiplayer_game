package com.game.gameworld;

import com.game.generics.Collideable;
import com.game.generics.Updateable;
import com.helper.AdvancedBoundingBox;

public class Collision implements Updateable {

    private Object objUp;
    private Object objRight;
    private Object objLeft;
    private Object objDown;

    private Collideable other;
    private AdvancedBoundingBox advancedBoundingBox;

    public Collision(AdvancedBoundingBox advancedBoundingBox, Collideable other) {
        this.advancedBoundingBox = advancedBoundingBox;
        // TODO not only world.getInstance
        this.other = other;
    }

    public boolean collides() {
        return isLeft() || isRight() || isUp() || isDown();
    }

    public boolean isRight() {
        return objRight != null;
    }

    public boolean isUp() {
        return objUp != null;
    }


    public boolean isDown() {
        return objDown != null;
    }


    public boolean isLeft() {
        return objLeft != null;
    }

    public String toString() {
        return String.format("%s %s %s %s", objUp, objLeft, objRight, objDown);
    }

    @Override
    public void update() {
        objUp = other.intersectsObject(advancedBoundingBox.getUp());
        objDown = other.intersectsObject(advancedBoundingBox.getDown());
        objLeft = other.intersectsObject(advancedBoundingBox.getLeft());
        objRight = other.intersectsObject(advancedBoundingBox.getRight());

    }
}
