package game;

import helper.Vector2I;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements Runnable {
    private List<Player> players;
    private Vector2I size;
    JFrame canvas;
    public World(Vector2I size) {
        players = new ArrayList<>();
        players.add(new Player(1));
        this.size = size;
        buildWorld();
        canvas = new JFrame("Game");
        canvas.setSize(size.getX(), size.getY());
        canvas.add(players.get(0));
        canvas.setVisible(true);

    }

    private void buildWorld() {

    }

    public void addPlayer(Player p) {

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        for(Player player:players) {
            player.paint(g);
        }
    }

    @Override
    public void run() {
        canvas.repaint();
    }

    public static void main(String[] args) {
        World w = new World(new Vector2I(100, 100));
        Thread worldThread = new Thread();
        worldThread.start();
    }
}
