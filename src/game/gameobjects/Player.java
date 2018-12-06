package game.gameobjects;

import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private BufferedImage bufferedImage;
    public Player() {
        setSize(new Vector2i(10, 10));
        this.setPosition(new Vector2f(0f, 0f));
    }

    @Override
    public void paint(Graphics g) {
        g.drawRect(Math.round(getPosition().getX()), Math.round(getPosition().getY()), getSize().getX(), getSize().getY());
    }
}
