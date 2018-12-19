package com.game.tiles;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.helper.BoundingBox;
import com.helper.Vector2f;
import com.helper.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends BoundingBox implements Collideable {
    private BufferedImage resultImg;

    public Tile(BufferedImage image, Vector2i position, Vector2i size) {
        this(image.getSubimage(position.getX(), position.getY(), size.getX(), size.getY()));
        System.out.println(position.toString() + " " + size.toString());
    }

    public Tile(BufferedImage img) {
        super(new Vector2f(0f,0f), new Vector2f(0f, 0f));
        this.resultImg = img;
    }

    public BufferedImage getResultImg() {
        return resultImg;
    }




    @Override
    public void paint(Graphics g) {
        if(World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            g.drawImage(resultImg,Math.round(getPosition().getX()), Math.round(getPosition().getY()), null);
        }
    }
}
