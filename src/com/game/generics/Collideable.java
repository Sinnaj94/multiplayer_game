package com.game.generics;

import com.helper.BoundingBox;

public interface Collideable {
    boolean intersects(BoundingBox collideable);

    BoundingBox createIntersection(BoundingBox collideable);

    BoundingBox getBoundingBox();
}
