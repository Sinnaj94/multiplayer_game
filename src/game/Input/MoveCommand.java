package game.Input;

import game.gameobjects.Player;
import game.generics.GameObject;
import helper.Vector2f;

public class MoveCommand implements Command {
    private Vector2f strength;

    public MoveCommand() {
        strength = new Vector2f(0f, 0f);
    }
    @Override
    public void execute(GameObject p) {
        p.move(strength);
    }

    @Override
    public Vector2f getStrength() {
        return strength;
    }

    @Override
    public void setStrength(Vector2f strength) {
        this.strength = strength;
    }
}
