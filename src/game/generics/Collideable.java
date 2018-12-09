package game.generics;

import helper.BoundingBox;

import java.awt.*;

public interface Collideable {
    boolean intersects(BoundingBox collideable);
    BoundingBox createIntersection(BoundingBox collideable);
    BoundingBox getBoundingBox();
}
