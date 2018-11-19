package game.tiles;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends JFrame {
    private BufferedImage img;

    public Tile(BufferedImage image, JSONObject object) {
        Graphics g = image.getGraphics();
        int x = (int) (long) object.get("x");
        int y = (int) (long) object.get("y");
        int w = (int) (long) object.get("width");
        int h = (int) (long) object.get("height");
        img = image.getSubimage(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {
        //TODO: Paint
    }
}
