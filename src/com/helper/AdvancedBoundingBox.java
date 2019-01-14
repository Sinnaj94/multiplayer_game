package com.helper;

public class AdvancedBoundingBox {
    private BoundingBox main;
    private BoundingBox down;

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    private float margin;

    public AdvancedBoundingBox(BoundingBox main) {
        this(main, 6f);
    }

    public AdvancedBoundingBox(BoundingBox main, float margin) {
        this.main = main;
        this.margin = margin;
        down = new BoundingBox(new Vector2f(0f, 0f), new Vector2f(margin, margin));
    }


    public BoundingBox getRight() {
        return new BoundingBox(new Vector2f(main.getX() + main.getWidth(), main.getY() +2f), new Vector2f(margin, main.getHeight() -4f));
    }

    public BoundingBox getLeft() {
        return new BoundingBox(new Vector2f(main.getX() - margin, main.getY() + 2f), new Vector2f(margin, main.getHeight() -4f ));
    }

    public BoundingBox getUp() {
        return new BoundingBox(new Vector2f(main.getX() + 2f, main.getY() - margin), new Vector2f(main.getWidth() -4f, margin));
    }

    public BoundingBox getDown() {
        down.getBoundingBox().setPosition(main.getX() + 2f, main.getY() + main.getHeight());
        down.getBoundingBox().setSize(new Vector2f(main.getWidth() - 4f, margin));
        return down;
    }
}
