package game.input;

import java.awt.*;

public interface Collideable {
    boolean collides(Collideable collideable);
    Rectangle boundingBox();
}
