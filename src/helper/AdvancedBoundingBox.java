package helper;

public class AdvancedBoundingBox {
    private BoundingBox main;

    public float getMargin() {
        return margin;
    }

    private float margin;
    public AdvancedBoundingBox(BoundingBox main) {
        this.main = main;
        margin = 10;
    }

    public BoundingBox getUp() {
        return new BoundingBox(new Vector2f(main.getX(), main.getY() - margin), new Vector2f(main.getWidth(), margin));
    }

    public BoundingBox getDown() {
        return new BoundingBox(new Vector2f(main.getX(), main.getY() + main.getHeight()), new Vector2f(main.getWidth(), margin));
    }
}
