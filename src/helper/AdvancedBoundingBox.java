package helper;

public class AdvancedBoundingBox {
    private BoundingBox main;
    private BoundingBox down;
    public float getMargin() {
        return margin;
    }

    private float margin;
    public AdvancedBoundingBox(BoundingBox main) {
        this.main = main;
        down = new BoundingBox(new Vector2f(0f,0f), new Vector2f(10f, 10f));
        margin = 10;
    }

    public BoundingBox getUp() {
        return new BoundingBox(new Vector2f(main.getX(), main.getY() - margin), new Vector2f(main.getWidth(), margin));
    }

    public BoundingBox getDown() {
        down.getBoundingBox().setPosition(new Vector2f(main.getX(), main.getY() + main.getHeight()));
        down.getBoundingBox().setSize(new Vector2f(main.getWidth(), margin));
        return down;
    }
}
