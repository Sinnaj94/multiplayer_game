package com.game;

import com.game.gameworld.players.Player;
import com.game.tiles.ResourceSingleton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    private BufferedImage heart;

    public UI() {
    }

    public void paint(Graphics g, Player p) {
        for(int i = 0 ; i < p.getLife(); i++) {
            g.drawImage(ResourceSingleton.getInstance().getHeart(), 10 + 20 * i,10,16,16,null);
        }
    }
}
