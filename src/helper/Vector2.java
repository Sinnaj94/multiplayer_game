package helper;

public class Vector2 {
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private float x;
    private float y;

    public Vector2(float x, float y) {
        // If x or y bigger than 1 -> Make it 1!
        if(Math.abs(x) > 1) {
            x = x / Math.abs(x);
        }
        if(Math.abs(y) > 1) {
            y = y / Math.abs(y);
        }
        this.x = x;
        this.y = y;
    }
}
