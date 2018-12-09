package game.gameworld;

import helper.BoundingBox;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends PhysicsObject {
    private BufferedImage bufferedImage;
    private Vector2f jumpAcceleration;
    Random r;
    private Bullet bullet;
    public Player(Vector2f position, Vector2f size) {
        super(position, size);
        r = new Random();
        //setSize(new Vector2i(16f, 16f));
        jumpAcceleration = new Vector2f(0f, -5f);
    }

    public Player(Vector2f position) {
        this(position, new Vector2f(16f, 16f));
    }


    public Player() {
        this(new Vector2f(0f,0f), new Vector2f(16f, 16f));
    }

    public void jump() {
        accelerate(new Vector2f(0f, r.nextFloat() * -4));
    }

    public boolean shoot() {
        if(bullet == null) {
            performShoot();
        } else if(bullet.ready()) {
            performShoot();
        }
        return false;
    }

    private void performShoot() {
        bullet = new Bullet(new Vector2f(getPosition().getX(), getPosition().getY()));
    }


    public void move(float direction) {
        this.getCurrentSpeed().addX(direction);
    }

    @Override
    public void update() {
        super.update();
        if(bullet!=null) {
            bullet.update();
        }
        shoot();
        if(getTouchesFloor()) {
            //jump();
            // TODO only when jump is requested
            //jump();
        }
    }

    @Override
    public void paint(Graphics g) {
        // TODO cooles Sprite einfügen
        if(World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            g.setColor(Color.red);
            Rectangle r = getBoundingBox().toIntRectangle();
            g.fillRect(r.x, r.y, r.width, r.height);
        }
        if(bullet != null) {
            bullet.paint(g);
        }
    }
}
