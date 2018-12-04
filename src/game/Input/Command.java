package game.Input;

import game.gameobjects.Player;
import game.generics.GameObject;
import helper.Vector2f;

public interface Command {
    void execute(GameObject p);
    Vector2f getStrength();
    void setStrength(Vector2f strength);
}

