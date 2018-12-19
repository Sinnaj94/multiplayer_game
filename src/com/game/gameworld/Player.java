package com.game.gameworld;

import com.helper.Vector2;
import com.helper.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Player extends PhysicsObject {
    private BufferedImage bufferedImage;
    private float jumpAcceleration;
    Random r;
    private Bullet bullet;
    private boolean jumpRequested;
    private Inventory inventory;
    private float movingSpeed;
    public Player(Vector2f position, Vector2f size) {
        super(position, size);
        r = new Random();
        inventory = new Inventory();
        //setSize(new Vector2i(16f, 16f));
        jumpAcceleration = -5f;
    }

    public Player(Vector2f position) {
        this(position, new Vector2f(16f, 16f));
    }


    public Player() {
        this(new Vector2f(0f,0f), new Vector2f(16f, 16f));
    }

    public void jump() {
        jumpRequested = true;
    }

    public boolean shoot() {
        /*if(bullet == null) {
            performShoot();
        } else if(bullet.ready()) {
            performShoot();
        }*/
        return false;
    }

    public boolean addItem(Item i) {
        return inventory.addItem(i);

    }

    private void performShoot() {
        bullet = new Bullet(new Vector2f(getPosition().getX(), getPosition().getY()));
    }


    public void move(float direction) {
        if(direction > 0 && movingSpeed <= 0) {
            this.movingSpeed += direction * 3f;
        } else if(direction < 0 && movingSpeed >= 0) {
            this.movingSpeed += direction * 3f;
        }
    }

    @Override
    public void update() {
        applyGravity();
        if(bullet!=null) {
            bullet.update();
        }
        //shoot();
        if(jumpRequested && getTouchesFloor()) {
            accelerate(new Vector2f(0f, jumpAcceleration));
        }
        jumpRequested = false;
        getCurrentSpeed().setX(movingSpeed);
        applySpeed();
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

    public class Inventory {
        List<Item> items;
        private int capacity;
        private int index;
        public Inventory() {
            capacity = 3;
            items = new ArrayList<>();
        }

        public boolean addItem(Item i) {
            if(index < capacity) {
                items.add(i);
                index++;
                System.out.println("Object added. New Length: " + items.size());
                return true;
            }
            return false;
        }

    }
}
