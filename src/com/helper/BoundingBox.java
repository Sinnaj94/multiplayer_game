package com.helper;

import com.game.generics.Collideable;
import com.game.generics.Placeable;
import com.game.generics.Renderable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BoundingBox implements Collideable, Placeable<Float, Vector2f>, Renderable {
    private Vector2f position;
    private Vector2f size;

    public BoundingBox(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public float getWidth() {
        return size.getX();
    }

    public float getHeight() {
        return size.getY();
    }

    @Override
    public boolean intersects(BoundingBox other) {
        return toRectangle().intersects(other.toRectangle());
    }

    public Rectangle.Float toRectangle() {
        return new Rectangle.Float(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Used for drawing.
     *
     * @return
     */
    public Rectangle toIntRectangle() {
        return new Rectangle(Math.round(getX()), Math.round(getY()), Math.round(getWidth()), Math.round(getHeight()));
    }

    @Override
    public BoundingBox createIntersection(BoundingBox other) {
        Rectangle2D.Float r = (Rectangle2D.Float) toRectangle().createIntersection(other.toRectangle());
        return new BoundingBox(new Vector2f(r.x, r.y), new Vector2f(r.width, r.height));
    }

    @Override
    public void setPosition(Vector2f position) {
        this.position.setVector(position);
    }

    @Override
    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public Vector2f getPosition() {
        return this.position;
    }

    @Override
    public void setSize(Vector2f size) {
        this.size.setVector(size);
    }

    public void setSize(float x, float y) {
        this.size.setX(x);
        this.size.setY(y);
    }

    @Override
    public Vector2f getSize() {
        return this.size;
    }

    @Override
    public void translate(Float x, Float y) {
        position.addX(x);
        position.addY(y);
    }

    @Override
    public void paint(Graphics g) {
        Rectangle r = toIntRectangle();
        g.setColor(Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
        g.setColor(Color.red);
        g.drawLine(r.x, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x + r.width, r.y, r.x, r.y + r.height);
    }
}
