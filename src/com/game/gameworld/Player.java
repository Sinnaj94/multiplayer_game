package com.game.gameworld;

import com.game.tiles.PlayerTilesetFactory;
import com.game.tiles.TilesetFactory;
import com.helper.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends PhysicsObject {
    private BufferedImage bufferedImage;
    private float jumpAcceleration;
    Random r;
    private Bullet bullet;
    private boolean jumpRequested;
    private Inventory inventory;
    private float movingSpeed;
    private Camera camera;
    private int test;
    private boolean moveLeft;
    private boolean moveRight;
    private String username;
    private int life;
    private PlayerTilesetFactory tilesetFactory;
    private boolean facesLeft;
    private float animationStep;
    public int getLife() {
        return life;
    }

    public Player(Vector2f position, Vector2f size) {
        super(position, size);
        r = new Random();
        inventory = new Inventory();
        jumpAcceleration = -5f;
        life = 5;
        tilesetFactory = new PlayerTilesetFactory();
    }


    public Player() {
        this(new Vector2f(0f, 0f), new Vector2f(32f, 32f));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Player(String username) {
        this();
        this.username = username;
    }

    public Player(Vector2f position, Vector2f size, int id) {
        super(position, size, id);
        r = new Random();
        inventory = new Inventory();
        jumpAcceleration = -5f;
        life = 5;
        tilesetFactory = new PlayerTilesetFactory();
    }


    public void jump() {
        jumpRequested = true;
    }

    public boolean shoot(Vector2f direction) {
        performShoot(direction);
        return false;
    }

    public boolean addItem(Item i) {
        return inventory.addItem(i);

    }

    private void performShoot(Vector2f direction) {
        if(bullet == null) {
            bullet = new Bullet(new Vector2f(getPosition().getX() + getSize().getX()/2, getPosition().getY() + getSize().getX() / 2), direction);
        }
    }


    public void move(float direction) {
        if (direction > 0 && movingSpeed <= 0) {
            this.movingSpeed += direction * 3f;
        } else if (direction < 0 && movingSpeed >= 0) {
            this.movingSpeed += direction * 3f;
        }
    }

    public void move(boolean left, boolean move) {
        if(left) {
            moveLeft = move;
            facesLeft = true;
        } else {
            moveRight = move;
            facesLeft = false;
        }
    }

    @Override
    public void update() {
        calculateGravity();
        if (bullet != null) {
            bullet.update();
            if(bullet.dies()) {
                bullet = null;
            }
        }
        float moveSpeed = 0;
        if(moveLeft) {
            moveSpeed-=3;
        }
        if(moveRight) {
            moveSpeed+=3;
        }
        move(new Vector2f(moveSpeed, 0f));
        if (jumpRequested && canJump()) {
            accelerate(new Vector2f(0f, jumpAcceleration));
        }
        jumpRequested = false;
        applySpeed();
        updateCollision();
    }

    private boolean canJump() {
        return getCollision().isDown();
    }

    @Override
    public void paint(Graphics g) {
        // TODO cooles Sprite einfügen
        //g.translate(++test, 0);
        Rectangle r = getBoundingBox().toIntRectangle();
        if (World.getInstance().DEBUG_DRAW) {
            //super.paint(g);
            if(facesLeft) {
                g.drawImage(tilesetFactory.getIdleLeft()[Math.round(animationStep)], toIntRectangle().x, toIntRectangle().y,32,32,null);
            } else {
                g.drawImage(tilesetFactory.getIdleRight()[Math.round(animationStep)], toIntRectangle().x, toIntRectangle().y, 32, 32, null);
            }
        } else {
            g.setColor(Color.red);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
        if (bullet != null) {
            bullet.paint(g);
        }
        g.drawString(username, r.x, r.y - 20);
        animationStep+=.05;
        if(animationStep > 3) {
            animationStep = 0;
        }
    }

    public String getUsername() {
        return username;
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
            if (index < capacity) {
                items.add(i);
                index++;
                return true;
            }
            return false;
        }
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.PLAYER;
    }
}
