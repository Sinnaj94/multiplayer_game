package com.game.gameworld;

import com.game.UI;
import com.game.generics.Renderable;
import com.game.input.InputLogic;
import com.helper.Vector2f;
import com.helper.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {
    private World world;
    private Vector2i size;
    private JFrame jFrame;
    private Canvas canvas;
    private InputLogic inputLogic;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2i SIZE = new Vector2i(500, 500);
    final long UPDATE_RATE = 10;
    private long lastTime;
    private int pos;
    private Camera camera;

    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    private float zoom;
    private GameObject watchable;

    public Renderer(String windowName) {
        this.running = true;
        world = World.getInstance();
        // TODO: Umschreiben
        jFrame = new JFrame(windowName);
        panel = (JPanel) jFrame.getContentPane();
        panel.setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        panel.setLayout(null);
        camera = new Camera(new Vector2i(0, 0), SIZE);
        canvas = new Canvas();
        canvas.setBounds(0, 0, SIZE.getX(), SIZE.getY());
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
        canvas.addMouseListener(new MyMouseListener());

    }

    public class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (World.getInstance().getTarget() != null) {
                GameObject t = World.getInstance().getTarget();
                double dx = e.getX() + camera.getPosition().getX() - t.getX() - (t.getSize().getX() / 2);
                double dy = e.getY() + camera.getPosition().getY() - t.getY() - (t.getSize().getX() / 2);
                double length = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                float adx = (float)(dx/length);
                float ady = (float)(dy/length);
                Vector2f direction = new Vector2f(adx, ady);
                System.out.println(direction);
                world.getTarget().shoot(direction);
            }


        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


    /*

     */

    /**
     * Thread for rendering with desired FPS
     */
    @Override
    public void run() {
        while (running) {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            synchronized (World.getInstance()) {
                if (System.currentTimeMillis() - lastTime > UPDATE_RATE) {
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
     *
     * @param g Graphics object
     */
    private void render(Graphics g) {
        // Render the world.
        GameObject o = World.getInstance().getTarget();
        if (o != null) {
            camera.observe(World.getInstance().getTarget());
            UI.paint(g, (Player)o);
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(-camera.getPosition().getX(), -camera.getPosition().getY());
        world.getLevel().paint(g2);
        // After that render the GameObjects.
        for (Renderable r : world.getRenderables().values()) {
            r.paint(g2);
        }
    }
}
