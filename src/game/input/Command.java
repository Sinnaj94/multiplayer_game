package game.input;

import helper.Vector2f;

public interface Command {
    void execute();
    Vector2f getStrength();
    void setStrength(Vector2f strength);
}

