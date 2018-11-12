package helper;

public class Vector2I implements Vector2<Integer, Vector2I> {
    private int x;
    private int y;

    public Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public void setX(Integer n) {
        this.x = n;
    }

    @Override
    public void setY(Integer n) {
        this.y = n;
    }

    @Override
    public void add(Vector2I v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    @Override
    public void multiply(Vector2I v) {
        this.x *= v.getX();
        this.y *= v.getY();
    }
}
