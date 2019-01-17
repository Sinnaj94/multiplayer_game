package com.game;

import com.game.gameworld.Player;
import com.game.generics.Renderable;
import com.game.tiles.ResourceSingleton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
