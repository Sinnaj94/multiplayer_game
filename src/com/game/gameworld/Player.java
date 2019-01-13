package com.game.gameworld;

import com.game.tiles.PlayerTilesetFactory;
import com.game.tiles.TilesetFactory;
import com.helper.BoundingBox;
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

    public Player(String username) {
        this(new BoundingBox(new Vector2f(0f, 0f), new Vector2f(32f, 32f)), username);
    }

    public Player(BoundingBox prototype, String username) {
        super(prototype);
        buildAttributes(username);
    }

    public Player(BoundingBox prototype, int id, String username) {
        super(prototype, id);
        buildAttributes(username);
    }

    private void buildAttributes(String username) {
        setUsername(username);
        r = new Random();
        inventory = new Inventory();
        jumpAcceleration = -5f;
        life = 5;
        tilesetFactory = new PlayerTilesetFactory();
    }

    public void setUsername(String username) {
        this.username = username;
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
        getCollision().update();
    }

    private boolean canJump() {
        return getCollision().isDown();
    }

    public PlayerState getPlayerState() {
        if(!getCollision().isDown()) {
            if(getCurrentSpeed().getY() > 0) {
                return PlayerState.FALLING;
            }
            return PlayerState.JUMPING;
        }
        if(moveLeft || moveRight) {
            return PlayerState.MOVING;
        }
        return PlayerState.IDLE;
    }

    @Override
    public void paint(Graphics g) {
        // TODO cooles Sprite einfügen
        //g.translate(++test, 0);
        Rectangle r = getBoundingBox().toIntRectangle();
        if (World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        } else {
            // TODO
            BufferedImage image = tilesetFactory.getAnimationFrame(getPlayerState(), facesLeft);
            g.drawImage(image, r.x - 5, r.y -5 , r.width +10, r.height + 10, null);
            tilesetFactory.update();
        }
        if (bullet != null) {
            bullet.paint(g);
        }
        g.setColor(Color.black);
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
