package game.Input;

import game.gameobjects.Player;
import helper.Vector2f;

public class MoveCommand implements Command {
    private Vector2f strength;
    @Override
    public void execute(Player p) {
        p.move(strength);
    }

    @Override
    public Vector2f getStrength() {
        return null;
    }

    @Override
    public Vector2f setStrength(Vector2f strength) {
        return null;
    }
}
