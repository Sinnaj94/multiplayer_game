package com.game.gameworld;

import com.game.generics.Renderable;
import com.helper.Vector2i;

import java.awt.*;

public class Sky implements Renderable {
    private ColorRect[] test;
    private int steps;
    public Sky(Vector2i size) {
        steps = 20;
        build(steps, size);
    }

    private void build(int steps, Vector2i size) {
        test = new ColorRect[steps];
        for(int i = 0; i < steps; i++) {
            float percentage = (float)i / steps;
            int y = (int)Math.floor(percentage * (float)size.getY());
            int height = (int)Math.ceil(((float)size.getY()/steps));
            test[i] = new ColorRect(getColor(percentage), new Rectangle(0, y, size.getX(), height));
        }
    }

    private Color getColor(float percentage) {
        int val = (int)Math.ceil(155 * (1-percentage) + 100);
        return new Color(val/2,val,val);
    }

    @Override
    public void paint(Graphics g) {
        if(World.getInstance().DEBUG_DRAW) {
        } else {
            for(int i = 0; i < test.length; i++) {
                test[i].paint(g);
            }
        }
    }

    private class ColorRect implements Renderable {
        public ColorRect(Color c, Rectangle r) {
            this.c = c;
            this.r = r;
        }

        public Color getC() {
            return c;
        }

        private Color c;

        public Rectangle getR() {
            return r;
        }

        private Rectangle r;

        @Override
        public void paint(Graphics g) {
            g.setColor(c);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
    }
}
