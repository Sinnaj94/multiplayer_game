package game.gameworld;

import helper.Vector2f;

import java.awt.*;

public class Bullet extends PhysicsObject {
    private int lifetime;
    private int maxLifetime;
    public Bullet(Vector2f position) {
        super(position, new Vector2f(2f, 2f));
        setMaxFallingSpeed(0);
        maxLifetime = 60;
        lifetime = 0;
        this.accelerate(new Vector2f(30f, 0f));
    }

    public boolean ready() {
        return lifetime > maxLifetime;
    }


    @Override
    public void update() {
        lifetime++;
        super.update();
    }

    @Override
    public void paint(Graphics g) {
        if(World.getInstance().DEBUG_DRAW) {
            super.paint(g);
        }
    }
}
