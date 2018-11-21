package helper;

public abstract class Vector2<Number, Vector2> {
    private Number x;
    private Number y;

    public Vector2(Number x, Number y) {
        setX(x);
        setY(y);
    }

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }

    public void setX(Number n) {
        this.x = n;
    }

    public void setY(Number n) {
        this.y = n;
    }

    public abstract void add(Vector2 v);
    public abstract void setVector(Vector2 v);
    public abstract void multiply(Vector2 v);

    @Override
    public String toString() {
        return "X: " + getX() + ", Y: " + getY();
    }
}
