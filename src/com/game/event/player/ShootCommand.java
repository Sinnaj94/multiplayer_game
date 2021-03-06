package com.game.event.player;

import com.game.gameworld.players.Player;
import com.game.gameworld.World;
import com.helper.Vector2f;

/**
 * Shoot command
 */
public class ShootCommand extends Command {
    public Vector2f getDirection() {
        return direction;
    }

    private Vector2f direction;

    /**
     *
     * @param id The shooters ID
     * @param direction The bullets Direction
     */
    public ShootCommand(int id, Vector2f direction) {
        super(id);
        this.direction = direction;
    }

    @Override
    public void execute(World.Accessor w) {
        if(!((Player)w.get(getId())).isResetRequested()) {
            w.addEvent(new ShootEvent(getId(), getDirection()));
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.SHOOT;
    }
}
