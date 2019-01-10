package com.game;

import com.game.gameworld.Player;
import com.game.generics.Renderable;

import java.awt.*;

public class UI {
    public static void paint(Graphics g, Player p) {
        g.drawString(String.format("LIVE: %d", p.getLife()), 10, 10);
    }
}
