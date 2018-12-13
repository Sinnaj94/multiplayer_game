package com.game.gameworld;

import com.game.input.InputLogic;
import com.game.input.MoveCommand;
import com.helper.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {
    private World world;
    private Vector2i size;
    private JFrame jFrame;
    private Canvas canvas;
    private InputLogic inputLogic;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2i SIZE = new Vector2i(600, 1000);
    final long UPDATE_RATE = 10;
    private long lastTime;
    // TODO: Manager auslagern
    public Renderer() {
        this.running = true;
        world = World.getInstance();
        // TODO: Umschreiben
        MoveCommand c = new MoveCommand();
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
        panel.setFocusable(true);
        panel.requestFocus();
        inputLogic = new InputLogic(panel);

    }
/*

*/
    /**
     * Thread for rendering with desired FPS
     */
    @Override
    public void run() {
        while(running) {
            synchronized (World.getInstance()) {
                if(System.currentTimeMillis() - lastTime > UPDATE_RATE) {
                    /*try {
                        World.getInstance().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    render();
                    lastTime = System.currentTimeMillis();
                } else {

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

    /**
     * Render Thread
     * @param g Graphics object
     */
    private void render(Graphics g) {
        // Render the world.
        world.getLevel().paint(g);
        // After that render the GameObjects.
        for(PhysicsObject ga:world.getDynamics()) {
            ga.paint(g);
        }
        for(GameObject s:world.getStatics()) {
            s.paint(g);
        }
        /*for(GameObject gameObject:world.getStatics()) {
            gameObject.paint(g);
        }*/
    }
}
