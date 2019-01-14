package com.game.generics;

import com.helper.BoundingBox;

public interface Collideable  {

    abstract boolean intersects(BoundingBox other);

    abstract BoundingBox createIntersection(BoundingBox collideable);

    abstract BoundingBox getBoundingBox();

}
