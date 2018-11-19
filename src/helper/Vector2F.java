package helper;

public class Vector2F implements Vector2<Float, Vector2F> {
    private float x;
    private float y;
    private boolean normalized;

    public Vector2F(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public Vector2F(float x, float y, boolean normalized) {
        this.normalized = true;
        this.setX(x);
        this.setY(y);
    }

    private float apply(float n) {
        if(normalized) if (Math.abs(n) > 1) {
            return n / Math.abs(n);
        }
        return n;
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
        this.x = apply(n);
    }

    @Override
    public void setY(Float n) {
        this.y = apply(n);
    }

    @Override
    public void add(Vector2F v) {
        setX(getX() + v.getX());
        setY(getY() + v.getY());
    }

    @Override
    public void multiply(Vector2F v) {
        setX(getX() * v.getY());
        setY(getY() * v.getY());
    }
}
