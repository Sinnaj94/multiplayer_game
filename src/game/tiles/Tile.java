package game.tiles;

import game.generics.GameObject;
import helper.Vector2f;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends GameObject {


    private BufferedImage resultImg;

    public Tile(BufferedImage image, JSONObject object) {
        super();
        Graphics g = image.getGraphics();
        int x = (int) (long) object.get("x");
        int y = (int) (long) object.get("y");
        int w = (int) (long) object.get("width");
        int h = (int) (long) object.get("height");
        resultImg = image.getSubimage(x, y, w, h);
    }

    public BufferedImage getResultImg() {
        return resultImg;
    }

    public Tile(BufferedImage img) {
        this.resultImg = img;
    }



    @Override
    public void paint(Graphics g) {
        g.drawImage(resultImg,Math.round(getPosition().getX()), Math.round(getPosition().getY()), null);
    }
}
