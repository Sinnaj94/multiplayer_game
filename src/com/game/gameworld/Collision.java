package com.game.gameworld;

public class Collision {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    public Collision() {

    }

    public boolean collides() {
        return left || right || up || down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public String toString() {
        return String.format("L: %b, R: %b, U: %b, D: %b", left, right, up, down);
    }
}
