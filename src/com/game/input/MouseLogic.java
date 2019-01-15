package com.game.input;

import com.game.render.RenderPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseLogic implements MouseListener {
    private RenderPanel renderPanel;

    public MouseLogic(RenderPanel renderPanel) {
        this.renderPanel = renderPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(renderPanel);
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
