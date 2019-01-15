package com.game;

import com.game.gameworld.Player;
import com.game.generics.Renderable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {
    private static BufferedImage heart;

    static {
        try {
            heart = ImageIO.read(new File("res/images/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void paint(Graphics g, Player p) {
        for(int i = 0 ; i < p.getLife(); i++) {
            g.drawImage(heart, 10 + 20 * i,10,16,16,null);
        }
    }
}
