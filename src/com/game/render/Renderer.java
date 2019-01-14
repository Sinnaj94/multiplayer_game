package com.game.render;

import com.game.UI;
import com.game.gameworld.*;
import com.game.generics.Renderable;
import com.game.input.InputLogic;
import com.helper.Vector2f;
import com.helper.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {
    private static final World world=World.getInstance();
    private World.Accessor accessor;
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
    private Sky sky;
    private LoadingScreen loadingScreen;
    private Object token;
    public Renderer(String windowName) {
        accessor = World.getInstance().getAccessor();
        this.running = true;
        // TODO: Umschreiben
        jFrame = new JFrame(windowName);
        panel = (JPanel) jFrame.getContentPane();
        panel.setLayout(new GridLayout(0, 2));
        panel.setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        panel.setLayout(null);
        camera = new Camera(new Vector2i(0, 0), SIZE);
        canvas = new Canvas();
        canvas.setBounds(0, 0, SIZE.getX(), SIZE.getY());
        canvas.setIgnoreRepaint(true);
        sky = new Sky(SIZE);
        panel.add(canvas);
        zoom = 1;
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        panel.setFocusable(true);
        panel.requestFocus();
        canvas.addMouseListener(new MyMouseListener());
        loadingScreen = new LoadingScreen();

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Vector2i s = new Vector2i(e.getComponent().getSize().width, e.getComponent().getSize().height);
                camera.setSize(s);
                sky = new Sky(s);
                canvas.setSize(e.getComponent().getSize());
            }
        });
    }


    public static void main(String[] args) {
        new Renderer("Das ist ein Test");
    }

    public void addToken(Object token) {
        this.token = token;
    }

    public class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (accessor.getTarget() != null) {
                GameObject t = accessor.getTarget();
                double dx = e.getX() + camera.getPosition().getX() - t.getX() - (t.getSize().getX() / 2);
                double dy = e.getY() + camera.getPosition().getY() - t.getY() - (t.getSize().getX() / 2);
                double length = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                float adx = (float)(dx/length);
                float ady = (float)(dy/length);
                Vector2f direction = new Vector2f(adx, ady);
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
        synchronized (World.getInstance()) {
            while (running) {
                try {
                    World.getInstance().wait();
                    render();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        GameObject o = accessor.getTarget();
        if(o!=null) {
            camera.observe(World.getInstance().getTarget());
            UI.paint(g, (Player)o);
        }
        sky.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(-camera.getPosition().getX(), -camera.getPosition().getY());
        world.getLevel().paint(g2);
        // After that render the GameObjects.
        for (Renderable r : accessor.getRenderables().values()) {
            r.paint(g2);
        }
    }

    private class LoadingScreen implements Renderable {
        @Override
        public void paint(Graphics g) {
            g.setFont(Font.getFont(Font.DIALOG));
            g.setColor(Color.black);
            g.drawString("Connecting to the Server...", 0, 0);
        }
    }
}