package game.tiles;

import helper.Vector2i;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloorTile extends Tile {
    public FloorTile(BufferedImage image, Vector2i position, Vector2i size) {
        super(image, position, size);
    }

    public FloorTile(BufferedImage resultImg) {
        super(resultImg);
    }
}
