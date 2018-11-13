package game;

import game.generics.Renderable;
import game.tiles.Tileset;
import helper.Vector2I;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel implements Runnable  {
    private World w;
    private Vector2I size;
    private JFrame canvas;

    public Renderer(World w) {
        Tileset t = new Tileset("res/tilesets/forest_tiles.json");
        this.w = w;
        this.size = w.getSize();
        canvas = new JFrame("Game");
        canvas.setSize(size.getX(), size.getY());
        canvas.setVisible(true);
    }

    private void build() {
        // Flush the frame - everything may be changed by now
        canvas.removeAll();
        // Add the players
        for(Player p:w.getPlayers()) {
            canvas.add(p);
        }
    }

    @Override
    public void paint(Graphics g) {
        for(Player p:w.getPlayers()) {
            p.paint(g);
        }
    }

    @Override
    public void run() {
        while(true) {
            build();
            paint(canvas.getGraphics());
            repaint();
        }
    }
}
