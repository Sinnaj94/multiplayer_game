package game.gameworld;

import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends PhysicsObject {
    private BufferedImage bufferedImage;
    public Player() {
        super();
        setSize(new Vector2i(16, 16));
        this.setPosition(new Vector2f(0f, 0f));
    }

    @Override
    public void paint(Graphics g) {
        // TODO cooles Sprite einfügen
        g.setColor(Color.red);
        g.fillRect((int)Math.floor(getPosition().getX()), (int)Math.floor(getPosition().getY()), getSize().getX(), getSize().getY());
    }
}
