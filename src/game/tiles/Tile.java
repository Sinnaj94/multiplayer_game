package game.tiles;

import game.gameworld.GameObject;
import game.gameworld.World;
import helper.Vector2i;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends GameObject {
    private BufferedImage resultImg;

    public Tile(BufferedImage image, Vector2i position, Vector2i size) {
        this(image.getSubimage(position.getX(), position.getY(), size.getX(), size.getY()));
        System.out.println(position.toString() + " " + size.toString());
    }

    public Tile(BufferedImage img) {
        super();
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
