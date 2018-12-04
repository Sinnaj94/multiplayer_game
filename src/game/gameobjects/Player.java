package game.gameobjects;

import game.generics.GameObject;
import game.generics.Renderable;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    public static int CURRENT_ID;
    private int id;
    private Vector2f position;
    private Vector2i size;
    private float speed;
    private BufferedImage bufferedImage;
    public Player() {
        // TODO: Set size and position...
        size = new Vector2i(10, 10);
    }

    @Override
    public void paint(Graphics g) {
        g.drawRect(Math.round(getPosition().getX()), Math.round(getPosition().getY()), size.getX(), size.getY());
    }
}
