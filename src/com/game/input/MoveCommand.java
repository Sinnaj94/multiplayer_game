package com.game.input;

import com.game.gameworld.PhysicsObject;
import com.game.gameworld.Player;
import com.helper.Vector2f;

public class MoveCommand implements Command {
    private Vector2f strength;
    private Player g;
    private Direction direction;
    public MoveCommand() {
        strength = new Vector2f(0f, 0f);
    }


    public MoveCommand(Vector2f strength) {
        this.strength = strength;
    }

    public void addGameObject(PhysicsObject g) {
        this.g = (Player)g;
    }


    @Override
    public void execute() {
        try {
            g.move(strength.getX());
        } catch(NullPointerException e) {

        }
    }

    @Override
    public Vector2f getStrength() {
        return strength;
    }

    @Override
    public void setStrength(Vector2f strength) {
        this.strength.setVector(strength);
    }
}
