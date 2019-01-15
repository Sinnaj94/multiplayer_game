package com.game.event.player;

import com.game.event.EventType;
import com.game.gameworld.Item;
import com.game.gameworld.Player;
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
        ((Player)w.getAccessor().get(getID())).addItem((Item) w.getAccessor().get(getItemID()));
    }

    @Override
    public EventType getEventType() {
        return EventType.ITEMGIVE;
    }
}
