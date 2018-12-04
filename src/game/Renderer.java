package game;

import game.Input.MoveCommand;
import game.gameobjects.Player;
import game.tiles.FloorTile;
import game.tiles.TilesetFactory;
import helper.Vector2f;
import helper.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Renderer implements Runnable {
    private World w;
    private Vector2i size;
    private JFrame jFrame;
    private Canvas canvas;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2i SIZE = new Vector2i(600, 400);
    final long D_FPS = 60;
    final long D_DELTA_LOOP = (1000 * 1000 * 1000)/D_FPS;
    private Vector2i direction;
    FloorTile[] a;
    private int tileSize;
    public Renderer(World w) {
        this.running = true;
        direction = new Vector2i(0, 0);
        this.w = w;
        MoveCommand c = new MoveCommand();
        /*canvas = new JFrame("Game");
        canvas.setSize(size.getX(), size.getY());
        canvas.setVisible(true);
        this.add(canvas);*/
        jFrame = new JFrame("Game");
        JPanel panel = (JPanel)jFrame.getContentPane();
        panel.setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0,0, SIZE.getX(), SIZE.getY());
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        // TODO

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.setFocusable(true);
        canvas.requestFocus();
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case 65: // a
                        c.setStrength(new Vector2f(-1f, 0f));
                        break;
                    case 87: // w
                        c.setStrength(new Vector2f(0f, -1f));
                        break;
                    case 68: // d
                        c.setStrength(new Vector2f(1f, 0f));
                        break;
                    case 83: // s
                        c.setStrength(new Vector2f(0f, 1f));
                        break;
                }
                System.out.println(e);
                w.addCommand(c);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case 65: // a
                        c.setStrength(new Vector2f(1f, 0f));
                        break;
                    case 87: // w
                        c.setStrength(new Vector2f(0f, 1f));
                        break;
                    case 68: // d
                        c.setStrength(new Vector2f(-1f, 0f));
                        break;
                    case 83: // s
                        c.setStrength(new Vector2f(0f, -1f));
                        break;
                }
                System.out.println(e);
                w.addCommand(c);
            }
        });
    }


    @Override
    public synchronized void run() {
        long beginLoopTime;
        long lastUpdateTime;
        long currentUpdateTime = System.nanoTime();
        long endLoopTime;
        long deltaLoop;
        while(running) {
            beginLoopTime = System.nanoTime();
            render();

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;
            if(deltaLoop > D_DELTA_LOOP) {

            } else {
                try {
                    Thread.sleep((D_DELTA_LOOP - deltaLoop)/(1000*1000));
                } catch(InterruptedException e) {

                }
            }
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, SIZE.getX(), SIZE.getY());
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    private void render(Graphics g) {
        // Render the world.
        w.getLevel().paint(g);
        // After that render the players.
        for(Player p:w.getPlayers()) {
            p.paint(g);
        }
    }
}
