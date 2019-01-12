package com.game.tiles;

import com.game.gameworld.World;
import com.game.generics.Collideable;
import com.helper.BoundingBox;
import com.helper.Vector2f;
import com.helper.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends BoundingBox implements Collideable {
    private BufferedImage resultImg;

    public Tile(BufferedImage image, Vector2i position, Vector2i size) {
        this(image.getSubimage(position.getX(), position.getY(), size.getX(), size.getY()));
    }

    public Tile(BufferedImage img) {
        super(new Vector2f(0f, 0f), new Vector2f(0f, 0f));
        this.resultImg = img;
    }

    public BufferedImage getResultImg() {
        return resultImg;
    }


    @Override
    public void paint(Graphics g) {
        if (World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            g.drawImage(resultImg, (int)Math.floor(getPosition().getX()), (int)Math.floor(getPosition().getY()), (int)Math.ceil(getSize().getX()), (int)Math.ceil(getSize().getY()), null);
        }
    }
}
