package com.game.gameworld.players;

import com.game.gameworld.*;
import com.game.tiles.PlayerTilesetFactory;
import com.game.tiles.ResourceSingleton;
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
    private int reloadSpeed;

    public boolean isResetRequested() {
        return isResetRequested;
    }

    private boolean isResetRequested;
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
        isResetRequested = true;
        currentTilesetFactory = ResourceSingleton.getInstance().getDead();
        setCurrentSpeed(new Vector2f(0f, 0f));
        accelerate(new Vector2f(0f, -1f));
        setGRAVITY(0f);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            respawn();
            isResetRequested = false;
        }).start();
    }

    private void respawn() {
        setGRAVITY(World.GRAVITY);
        currentTilesetFactory = tilesetFactory;
        isDead = false;
        setPosition(0, -World.TILE_SIZE * 2);
        setCurrentSpeed(new Vector2f(0f, 0f));
        life = 5;
    }

    private boolean aiPlayer;
    Random r;
    private boolean jumpRequested;
    private Inventory inventory;
    private float movingSpeed;
    private boolean moveLeft;

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    private boolean moveRight;
    private String username;
    private int life;
    private PlayerTilesetFactory tilesetFactory;
    private PlayerTilesetFactory currentTilesetFactory;
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
        cooldownTime = reloadSpeed;
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
        currentTilesetFactory = tilesetFactory;
    }

    public void setReloadSpeed(int reloadSpeed) {
        this.reloadSpeed = reloadSpeed;
    }

    private int visibleDamage;

    private void buildAttributes(String username) {
        setUsername(username);
        r = new Random();
        inventory = new Inventory();
        jumpAcceleration = -5f;
        life = 5;
        setTilesetFactory(ResourceSingleton.getInstance().getPlayers());
        aiPlayer = false;
        walkingSpeed = 3f;
        shoot = false;
        isDead = false;
        reloadSpeed = 10;
        isResetRequested = false;
        visibleDamage = 0;

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
            if(visibleDamage > 0) {
                visibleDamage--;
                currentTilesetFactory = ResourceSingleton.getInstance().getDamage();
            } else if(visibleDamage == 0) {
                currentTilesetFactory = tilesetFactory;
                visibleDamage = -1;
            }
            BufferedImage image = currentTilesetFactory.getAnimationFrame(getPlayerState(), isFacesLeft());

            g.drawImage(image, r.x -6, r.y -2 , r.width + 12, r.height + 5, null);
        }
        g.setColor(Color.black);
        g.drawString(username, r.x, r.y - 20);
        animationStep+=.05;
        if(animationStep > 3) {
            animationStep = 0;
        }
    }

    public void addHealth(int health) {
        life += health;
    }

    public void hit(int damage) {
        accelerate(new Vector2f(0f, -.1f));
        damage(damage);
    }

    private void damage(int damage) {
        if(life - damage >= 0) {
            life-=damage;
            if(life == 0) {
                isDead = true;
            } else {
                visibleDamage = 4;

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
