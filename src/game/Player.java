package game;

import game.generics.Moveable;
import game.generics.Renderable;
import helper.Vector2F;
import helper.Vector2I;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Player extends JPanel implements Renderable {
    public static int CURRENT_ID;
    private int id;
    private Vector2I position;
    private Vector2I size;
    private float speed;

    private BufferedImage bufferedImage;

    public Player() {
        //super();
        //this.id = id;
        this.speed = 0.1f;
        // TODO: Set size and position...
        position = new Vector2I(0,0);
        size = new Vector2I(10, 10);
    }

    public void move() {
        this.position.add(new Vector2I(1,1));
    }

    @Override
    public void paint(Graphics g) {
        // TODO: Improve...
        g.drawRect(position.getX(), position.getY(), size.getX(), size.getY());
    }

}
