package game.Input;

import game.gameobjects.GameObject;
import helper.Vector2f;

public class MoveCommand implements Command {
    private Vector2f strength;
    private GameObject g;
    public MoveCommand() {
        strength = new Vector2f(0f, 0f);
    }

    public MoveCommand(Vector2f strength) {
        this.strength = strength;
    }

    public void addGameObject(GameObject g) {
        this.g = g;
    }


    @Override
    public void execute() {
        g.move(strength);
    }

    @Override
    public Vector2f getStrength() {
        return strength;
    }

    @Override
    public void setStrength(Vector2f strength) {
        this.strength.setX(strength.getX());
        this.strength.setY(strength.getY());
    }
}
