package game.generics;

import java.awt.*;

public interface Collideable {
    boolean intersects(Collideable collideable);
    Rectangle boundingBox();
}
