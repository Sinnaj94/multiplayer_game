package com.game.gameworld;

import com.game.input.Command;
import com.game.input.InputListener;
import com.game.input.InputLogic;
import com.game.input.MoveCommand;
import com.helper.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {
    private World world;
    private Vector2i size;
    private JFrame jFrame;
    private Canvas canvas;
    private InputLogic inputLogic;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2i SIZE = new Vector2i(400, 400);
    final long UPDATE_RATE = 10;
    private long lastTime;
    private int pos;
    private Camera camera;
    private JPanel panel;
    private float zoom;
    // TODO: Manager auslagern
    public Renderer() {
        this.running = true;
        world = World.getInstance();
        // TODO: Umschreiben
        MoveCommand c = new MoveCommand();
        jFrame = new JFrame("Game");
        panel = (JPanel)jFrame.getContentPane();
        panel.setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        panel.setLayout(null);
        camera = new Camera(new Vector2i(0, 0), SIZE, world.getPlayers().get(0));
        canvas = new Canvas();
        canvas.setBounds(0,0, SIZE.getX(), SIZE.getY());
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);
        zoom = 1;
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
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("Focus gained");

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("Focus lost");
            }
        });
    }
/*

*/
    /**
     * Thread for rendering with desired FPS
     */
    @Override
    public void run() {
        while(running) {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            synchronized (World.getInstance()) {
                if(System.currentTimeMillis() - lastTime > UPDATE_RATE) {
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
        camera.update();
        Graphics2D g2 = (Graphics2D)g.create();
        zoom *= .999f;
        g2.scale(zoom, zoom);
        g2.translate(-camera.getPosition().getX(), -camera.getPosition().getY());
        world.getLevel().paint(g2);
        // After that render the GameObjects.
        for(PhysicsObject ga:world.getDynamics()) {
            ga.paint(g2);
        }
        for(GameObject s:world.getStatics()) {
            s.paint(g2);
        }
        /*for(GameObject gameObject:world.getStatics()) {
            gameObject.paint(g);
        }*/
    }
}
