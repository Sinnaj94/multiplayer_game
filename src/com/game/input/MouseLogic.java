package com.game.input;

import com.game.event.player.Command;
import com.game.event.player.ShootCommand;
import com.game.event.player.ShootEvent;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.game.render.RenderPanel;
import com.helper.Vector2f;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.Queue;

public class MouseLogic implements MouseListener {
    private RenderPanel renderPanel;
    private World.Accessor accessor;
    private Queue<Command> commandQueue;

    public Queue<Command> getCommandQueue() {
        return commandQueue;
    }

    public MouseLogic(RenderPanel renderPanel) {
        this.renderPanel = renderPanel;
        accessor = World.getInstance().getAccessor();
        commandQueue = new ArrayDeque<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(accessor.getTarget() != null) {
            GameObject t = accessor.getTarget();
            double dx = e.getX() + renderPanel.getCamera().getPosition().getX() - t.getX() - (t.getSize().getX() / 2);
            double dy = e.getY() + renderPanel.getCamera().getPosition().getY() - t.getY() - (t.getSize().getX() / 2);
            double length = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

            float adx = (float)(dx/length);
            float ady = (float)(dy/length);
            Vector2f direction = new Vector2f(adx, ady);
            commandQueue.add(new ShootCommand(accessor.getTarget().getID(), direction));

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
