package com.game.generics;

import com.game.gameworld.players.Player;

public interface Collectable {
    boolean canTake(Player p);
    void give(Player p);
}
