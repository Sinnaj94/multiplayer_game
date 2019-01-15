package com.game.event.player;

import com.game.event.EventType;
import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.game.gameworld.World;

/**
 * Move event - Move the Player
 */
public class MoveEvent extends PlayerEvent {
    public boolean isLeft() {
        return left;
    }

    public boolean isMove() {
        return move;
    }

    private boolean left;
    private boolean move;
    public MoveEvent(int id, boolean left, boolean move) {
        super(id);
        this.left = left;
        this.move = move;
    }

    @Override
    public void execute(World w) {
        GameObject t = w.getAccessor().get(getID());
        if(t instanceof Player) {
            ((Player)t).move(left, move);
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.PLAYERMOVE;
    }
}
