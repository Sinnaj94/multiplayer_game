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

    public boolean isDead() {
        return isDead;
    }

    private boolean isDead;
    public void setAiPlayer(boolean aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public boolean isAiPlayer() {
        return aiPlayer;
    }

    public void reset() {
        isDead = false;
        setPosition(0, 0);
        life = 5;
    }

    private boolean aiPlayer;
    Random r;
    private boolean jumpRequested;
    private Inventory inventory;
    private float movingSpeed;
    private boolean moveLeft;
    private boolean moveRight;
    private String username;
    private int life;
    private PlayerTilesetFactory tilesetFactory;
    private boolean facesLeft;
    private Vector2f shootDirection;
    private boolean shoot;
    private int cooldownTime;

    public boolean isShoot() {
        return shoot;
    }

    public Vector2f getShootDirection() {
        return shootDirection;
    }

    public void setShoot(Vector2f direction) {
        shootDirection = direction;
        this.shoot = true;
    }

    public void shot() {
        this.shoot = false;
        cooldownTime = 100;
    }

    public boolean canShoot() {
        return cooldownTime == 0;
    }

    private Bullet bullet;
    public float getWalkingSpeed() {
        return walkingSpeed;
    }

    public void setWalkingSpeed(float walkingSpeed) {
        this.walkingSpeed = walkingSpeed;
    }

    private float walkingSpeed;
    private float animationStep;
    public int getLife() {
        return life;
    }

    public Player(String username) {
        this(username, new Vector2f(0f, -200f));
    }

    public Player(String username, Vector2f position) {
        this(new BoundingBox(position, new Vector2f(22f, 32f)), username);
    }

    public Player(BoundingBox prototype, String username) {
        super(prototype);
        buildAttributes(username);
    }

    public Player(BoundingBox prototype, int id, String username) {
        super(prototype, id);
        buildAttributes(username);
    }

    public float getJumpAcceleration() {
        return jumpAcceleration;
    }

    public void setJumpAcceleration(float jumpAcceleration) {
        this.jumpAcceleration = jumpAcceleration;
    }

    public void setTilesetFactory(PlayerTilesetFactory tilesetFactory) {
        this.tilesetFactory = tilesetFactory;
    }

    private void buildAttributes(String username) {
        setUsername(username);
        r = new Random();
        inventory = new Inventory();
        jumpAcceleration = -5f;
        life = 5;
        tilesetFactory = new PlayerTilesetFactory("res/tilesets/person_tiles.json");
        aiPlayer = false;
        walkingSpeed = 3f;
        shoot = false;
        isDead = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void jump() {
        jumpRequested = true;
    }

    public boolean addItem(Item i) {
        return inventory.addItem(i);
    }

    public boolean canTake() {
        return !inventory.isFull();
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
        float moveSpeed = 0;
        if(moveLeft) {
            moveSpeed-=walkingSpeed;
        }
        if(moveRight) {
            moveSpeed+=walkingSpeed;
        }
        move(new Vector2f(moveSpeed, 0f));
        if (jumpRequested && canJump()) {
            accelerate(new Vector2f(0f, jumpAcceleration));
        }
        jumpRequested = false;
        translate(getCurrentSpeed().getX(), getCurrentSpeed().getY());
        getCollisionCache().update();
        if(cooldownTime > 0) {
            cooldownTime--;
        }
    }

    private boolean canJump() {
        return getCollisionCache().is(Direction.DOWN);
    }

    public PlayerState getPlayerState() {
        if(!getCollisionCache().is(Direction.DOWN)) {
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

            BufferedImage image = tilesetFactory.getAnimationFrame(getPlayerState(), isFacesLeft());
            g.drawImage(image, r.x -6, r.y -2 , r.width + 12, r.height + 5, null);
            tilesetFactory.update();
        }
        g.setColor(Color.black);
        g.drawString(username, r.x, r.y - 20);
        animationStep+=.05;
        if(animationStep > 3) {
            animationStep = 0;
        }
    }

    public void hit(int damage) {
        //accelerate(new Vector2f(0f, -1f));
        damage(damage);
    }

    private void damage(int damage) {
        if(life - damage >= 0) {
            life-=damage;
            if(life == 0) {
                isDead = true;
            }
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
            index = 0;
            capacity = 3;
            items = new ArrayList<>();
        }

        public boolean addItem(Item i) {
            if (!isFull()) {
                items.add(i);
                index++;
                return true;
            }
            return false;
        }

        public boolean isFull() {
            return index >= capacity;
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d", getGameObjectType(), username, getID());
    }



    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.PLAYER;
    }
}
