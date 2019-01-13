package com.game.generics;

import com.helper.BoundingBox;

public interface Collideable {
    boolean intersects(BoundingBox other);

    Object intersectsObject(BoundingBox other);

    BoundingBox createIntersection(BoundingBox collideable);

    BoundingBox getBoundingBox();
}
