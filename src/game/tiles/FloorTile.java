package game.tiles;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloorTile extends Tile {
    public FloorTile(BufferedImage image, JSONObject object) {
        super(image, object);
    }

    public FloorTile(BufferedImage resultImg) {
        super(resultImg);
    }
}
