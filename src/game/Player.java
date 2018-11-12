package game;

import helper.Vector2F;
import helper.Vector2I;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Player extends JPanel {
    public static int CURRENT_ID;
    private int id;
    private Vector2I position;
    private Vector2I size;
    private float speed;

    private BufferedImage bufferedImage;

    public Player(int id) {
        this.id = id;
        this.speed = 0.1f;
        // TODO: Set size and position...
        position = new Vector2I(0,0);
        size = new Vector2I(10, 10);
    }


    @Override
    public void paint(Graphics g) {
        // TODO
        g.drawRect(position.getX(), position.getY(), size.getX(), size.getY());
    }

}
