package game.tiles;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FloorTile extends JPanel {
    private BufferedImage img;

    public FloorTile(BufferedImage image, JSONObject object) {
        Graphics g = image.getGraphics();
        int x = (int)(long)object.get("x");
        int y = (int)(long)object.get("y");
        int w = (int)(long)object.get("width");
        int h = (int)(long)object.get("height");
        img = image.getSubimage(x,y,w,h);
    }

    @Override
    public void paint(Graphics g) {
        //TODO
    }
}
