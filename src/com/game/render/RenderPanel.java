package com.game.render;

import com.game.UI;
import com.game.gameworld.*;
import com.game.generics.Renderable;
import com.helper.Vector2f;
import com.helper.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class RenderPanel extends JPanel implements Runnable {
    private World.Accessor accessor;
    final Vector2i SIZE = new Vector2i(500, 500);

    public Camera getCamera() {
        return camera;
    }

    private Camera camera;
    private float zoom;
    public volatile boolean running = true;
    private UI ui;

    public RenderPanel() {
        super();
        accessor = World.getInstance().getAccessor();
        zoom = 1;
        camera = new Camera(SIZE);
        setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        setMaximumSize(new Dimension(SIZE.getX(), SIZE.getY()));
        setSize(new Dimension(SIZE.getX(), SIZE.getY()));
        ui = new UI();
    }

    public void zoom(float delta) {
        zoom+=delta;
    }


    @Override
    public void paint(Graphics g) {
        GameObject o = accessor.getTarget();
        if(o!=null) {
            camera.observe(World.getInstance().getTarget());
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(-camera.getPosition().getX(), -camera.getPosition().getY());
        if(zoom!=1) {
            g2.scale(zoom, zoom);
        }
        g.setColor(new Color(0xf87ceeb));
        g.fillRect(0,0, SIZE.getX(), SIZE.getY());
        accessor.getLevel().paint(g2);
        // After that render the GameObjects.
        Iterator<Renderable> i = accessor.getRenderables().values().iterator();
        try {
            while(i.hasNext()) {
                Renderable r = i.next();
                if(r != o) {
                    r.paint(g2);
                }
            }
        } catch(ConcurrentModificationException e) {

        }

        if(o!=null) {
            o.paint(g2);
        }
        if(o instanceof Player) {
            ui.paint(g, (Player)o);
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
