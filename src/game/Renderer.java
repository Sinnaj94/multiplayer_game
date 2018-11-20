package game;

import game.tiles.TilesetFactory;
import helper.Vector2I;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {
    private World w;
    private Vector2I size;
    private JFrame jFrame;
    private Canvas canvas;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2I SIZE = new Vector2I(600, 400);
    final long D_FPS = 60;
    final long D_DELTA_LOOP = (1000 * 1000 * 1000)/D_FPS;
    public Renderer(World w) {
        this.running = true;
        TilesetFactory t = new TilesetFactory("res/tilesets/forest_tiles.json");
        this.w = w;
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

        canvas.addMouseListener(new MouseControl());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
    }

    private class MouseControl extends MouseAdapter {

    }

    @Override
    public void run() {
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
            update((int)(currentUpdateTime - lastUpdateTime)/(1000*1000));

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

    // Game logic! TODO: export
    protected void update(int deltaTime) {
        for(Player p:w.getPlayers()) {
            p.move();
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, SIZE.getX(), SIZE.getY());
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    protected void render(Graphics g) {
        for(Player p:w.getPlayers()) {
            p.paint(g);
        }
    }
}
