package helper;

public interface Vector2<Number, Vector2> {
    Number getX();
    Number getY();
    void setX(Number n);
    void setY(Number n);
    void add(Vector2 v);
    void multiply(Vector2 v);
}
