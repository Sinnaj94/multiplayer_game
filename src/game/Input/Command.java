package game.Input;

import game.gameobjects.Player;
import helper.Vector2f;

public interface Command {
    void execute(Player p);
    Vector2f getStrength();
    Vector2f setStrength(Vector2f strength);
}

