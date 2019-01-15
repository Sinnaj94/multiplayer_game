package com.game.generics;

import com.game.gameworld.Player;

public interface Collectable {
    boolean canTake(Player p);
    void give(Player p);
}
