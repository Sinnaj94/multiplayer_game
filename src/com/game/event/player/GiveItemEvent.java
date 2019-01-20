package com.game.event.player;

import com.game.event.EventType;
import com.game.gameworld.players.Player;
import com.game.gameworld.World;

/**
 * Assign Item to Player
 */
public class GiveItemEvent extends PlayerEvent {
    public int getItemID() {
        return itemID;
    }

    int itemID;
    public GiveItemEvent(int playerID, int itemID) {
        super(playerID);
        this.itemID = itemID;
    }

    @Override
    public void execute(World w) {
        ((Player)w.getAccessor().get(getID())).addHealth(1);
    }

    @Override
    public EventType getEventType() {
        return EventType.ITEMGIVE;
    }
}
