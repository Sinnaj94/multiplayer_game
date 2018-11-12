package helper;

public class Vector2F implements Vector2<Float, Vector2F> {
    private float x;
    private float y;

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Float getX() {
        return x;
    }

    @Override
    public Float getY() {
        return y;
    }

    @Override
    public void setX(Float n) {
        this.x = n;
    }

    @Override
    public void setY(Float n) {
        this.y = n;
    }

    @Override
    public void add(Vector2F v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    @Override
    public void multiply(Vector2F v) {
        this.x *= v.getX();
        this.y *= v.getY();
    }
}
