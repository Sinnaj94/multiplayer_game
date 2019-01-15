package com.game.render;

import com.game.UI;
import com.game.gameworld.*;
import com.game.generics.Renderable;
import com.helper.Vector2i;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel implements Runnable {
    private World.Accessor accessor;
    private Sky sky;
    final Vector2i SIZE = new Vector2i(500, 500);
    private Camera camera;
    public volatile boolean running = true;

    public RenderPanel() {
        super();
        accessor = World.getInstance().getAccessor();
        sky = new Sky(SIZE);
        camera = new Camera(SIZE);
        setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        setMaximumSize(new Dimension(SIZE.getX(), SIZE.getY()));
        setSize(new Dimension(SIZE.getX(), SIZE.getY()));
    }



    @Override
    public void paint(Graphics g) {
        GameObject o = accessor.getTarget();
        if(o!=null) {
            camera.observe(World.getInstance().getTarget());
            if(o instanceof Player) {
                UI.paint(g, (Player)o);
            }
        }
        sky.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(-camera.getPosition().getX(), -camera.getPosition().getY());
        accessor.getLevel().paint(g2);
        // After that render the GameObjects.
        for (Renderable r : accessor.getRenderables().values()) {
            r.paint(g2);
        }
    }

    @Override
    public void run() {
        synchronized (World.getInstance()) {
            while (running) {
                try {
                    World.getInstance().wait();
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
