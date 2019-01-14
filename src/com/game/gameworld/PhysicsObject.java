package com.game.gameworld;

import com.game.generics.Collideable;
import com.game.generics.Updateable;
import com.helper.AdvancedBoundingBox;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class PhysicsObject extends GameObject {
    private float friction;
    private final float GRAVITY = 0.089f;
    private World w;
    private World.Accessor accessor;

    public void setMaxFallingSpeed(float maxFallingSpeed) {
        this.maxFallingSpeed = maxFallingSpeed;
    }

    public float getMaxFallingSpeed() {
        return maxFallingSpeed;
    }

    public Vector2f getImpulse() {
        return impulse;
    }

    public void setImpulse(Vector2f impulse) {
        this.impulse = impulse;
    }

    private float maxFallingSpeed;
    private float acceleration;
    private Vector2f impulse;
    private float maxRunningSpeed;
    private float jumpRequest;

    public CollisionCache getCollisionCache() {
        return collisionCache;
    }

    private CollisionCache collisionCache;

    public Vector2f getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Vector2f currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    // Speed is a variable, which is multiplied with the position
    private Vector2f currentSpeed;
    private Vector2f constantForceRequest;

    public AdvancedBoundingBox getAdvancedBoundingBox() {
        return advancedBoundingBox;
    }

    private AdvancedBoundingBox advancedBoundingBox;
    private float bounciness;
    private boolean touchesFloor;

    public void setConstantForceRequest(Vector2f constantForceRequest) {
        this.constantForceRequest = constantForceRequest;
    }

    // TODO: Merge constructors
    public PhysicsObject(BoundingBox prototype) {
        super(prototype);
        buildAttributes();
    }

    public PhysicsObject(BoundingBox prototype, int id) {
        super(prototype, id);
        buildAttributes();
    }

    private void buildAttributes() {
        advancedBoundingBox = new AdvancedBoundingBox(getBoundingBox());
        maxRunningSpeed = 2f;
        acceleration = .01f;
        currentSpeed = new Vector2f(0f, 0f);
        constantForceRequest = new Vector2f(0f, 0f);
        jumpRequest = 0f;
        bounciness = 0.2f;
        maxFallingSpeed = 5f;
        friction = 0.9f;
        impulse = new Vector2f(0f, 0f);
        w = World.getInstance();
        accessor = World.getInstance().getAccessor();
        collisionCache = new CollisionCache(getBoundingBox(), advancedBoundingBox, accessor.getLevel());
    }

    public void setBounciness(float bounciness) {
        this.bounciness = bounciness;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxRunningSpeed(float maxRunningSpeed) {
        this.maxRunningSpeed = maxRunningSpeed;
    }

    public void calculateGravity() {
        this.currentSpeed.addY(GRAVITY);
        if (this.currentSpeed.getY() > maxFallingSpeed && maxFallingSpeed != 0) {
            this.currentSpeed.setY(maxFallingSpeed);
        }
    }

    public void move(Vector2f direction) {
        translate(direction.getX(), 0f);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void translate(Float x, Float y) {
        // Check down collisions
        if(y > 0) {
            if(!collisionCache.is(Direction.DOWN)) {
                super.translate(0f, y);
            } else {
                float distance = advancedBoundingBox.getMargin() - collisionCache.get(Direction.DOWN).getHeight();
                if(distance > y) {
                    super.translate(0f, y);
                } else {
                    super.translate(0f, distance);
                    if(currentSpeed.getY() > 0) {
                        currentSpeed.setY(currentSpeed.getY() * -bounciness);
                    }
                }
            }
        } else if(y < 0) {
            if(!collisionCache.is(Direction.UP)) {
                super.translate(0f, y);
            } else {
                float distance = advancedBoundingBox.getMargin() - collisionCache.get(Direction.UP).getWidth();
                if(distance < y) {
                    super.translate(x, 0f);
                } else {
                    super.translate(-distance, 0f);
                    if(currentSpeed.getX() > 0) {
                        currentSpeed.setX(0f);
                    }
                }
            }
        }

        if(x > 0) {
            if(!collisionCache.is(Direction.RIGHT)) {
                super.translate(x, 0f);
            } else {
                float distance = advancedBoundingBox.getMargin() - collisionCache.get(Direction.RIGHT).getWidth();
                if(distance > x) {
                    super.translate(x, 0f);
                } else {
                    super.translate(distance, 0f);
                    if(currentSpeed.getX() > 0) {
                        currentSpeed.setX(0f);
                    }
                }
            }
        } else if(x < 0) {
            if(!collisionCache.is(Direction.LEFT)) {
                super.translate(x, 0f);
            } else {
                float distance = advancedBoundingBox.getMargin() - collisionCache.get(Direction.LEFT).getWidth();
                if(distance < x) {
                    super.translate(x, 0f);
                } else {
                    super.translate(-distance, 0f);
                    if(currentSpeed.getX() > 0) {
                        currentSpeed.setX(0f);
                    }
                }
            }
        }

    }

    public void accelerate(Vector2f speed) {
        getCurrentSpeed().add(speed);
    }


    @Override
    public void update() {
        calculateGravity();
        translate(currentSpeed.getX(), currentSpeed.getY());
        collisionCache.update();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        advancedBoundingBox.getUp().paint(g);
        advancedBoundingBox.getDown().paint(g);
        advancedBoundingBox.getRight().paint(g);
        advancedBoundingBox.getLeft().paint(g);
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.PHYSICSOBJECT;
    }

    public class CollisionCache implements Updateable {
        private Map<Direction, BoundingBox> collisions;
        private AdvancedBoundingBox bb;
        private Collideable map;
        private BoundingBox middle;
        public CollisionCache(BoundingBox middle, AdvancedBoundingBox bb, Collideable map) {
            this.bb = bb;
            this.map = map;
            collisions = new HashMap<>();
            this.middle = middle;
        }

        public Map<Direction, BoundingBox> getCollisions() {
            return collisions;
        }

        public boolean is(Direction d) {
            return collisions.get(d) != null;
        }

        public BoundingBox get(Direction d) {
            return collisions.get(d);
        }


        @Override
        public void update() {
            collisions.put(Direction.MIDDLE, map.createIntersection(middle));
            collisions.put(Direction.LEFT, map.createIntersection(bb.getLeft()));
            collisions.put(Direction.RIGHT, map.createIntersection(bb.getRight()));
            collisions.put(Direction.UP, map.createIntersection(bb.getUp()));
            collisions.put(Direction.DOWN, map.createIntersection(bb.getDown()));
        }
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN, MIDDLE
    }
}
