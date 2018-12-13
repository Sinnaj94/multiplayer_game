package com.game.tiles;

import com.helper.Vector2i;

import java.awt.image.BufferedImage;

public class FloorTile extends Tile {
    public FloorTile(BufferedImage image, Vector2i position, Vector2i size) {
        super(image, position, size);
    }

    public FloorTile(BufferedImage resultImg) {
        super(resultImg);
    }
}
