package helper;

/**
 * Vector2 Float implementation
 */
public class Vector2f extends Vector2<Float, Vector2f> {
    private float x;
    private float y;
    public Vector2f(Float x, Float y) {
        super(x, y);
    }

    @Override
    public void add(Vector2f v) {
        setX(getX() + v.getX());
        setY(getY() + v.getY());
    }

    @Override
    public void setVector(Vector2f v) {
        setX(v.getX());
        setY(v.getY());
    }

    @Override
    public void multiply(Vector2f v) {
        setX(getX() * v.getY());
        setY(getY() * v.getY());
    }
}
