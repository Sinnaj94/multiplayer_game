package com.game.input;

import com.game.event.player.Command;
import com.game.event.player.JumpCommand;
import com.game.event.player.MoveCommand;
import com.game.event.player.ShootCommand;
import com.game.gameworld.World;
import com.helper.Vector2;
import com.helper.Vector2f;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Queue;

public class InputLogic {
    private JComponent component;

    public Queue<Command> getCommandQueue() {
        return commandQueue;
    }

    private Queue<Command> commandQueue;
    private int commandCount;
    private World.Accessor accessor;

    public InputLogic(JComponent component) {
        accessor = World.getInstance().getAccessor();
        commandQueue = new ArrayDeque<>();
        //myKeyListener = new MyKeyListener();
        //component.addKeyListener(myKeyListener);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Pressed.up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "Released.up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Pressed.down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "Released.down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "Pressed.left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "Released.left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "Pressed.right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "Released.right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Jump");


        actionMap.put("Pressed.left", requestLeft());
        actionMap.put("Released.left", requestLeftStop());
        actionMap.put("Pressed.right", requestRight());
        actionMap.put("Released.right", requestRightStop());

        actionMap.put("Jump", jumpRequest());

    }


    private Action requestLeft() {
        return new MoveAction(true, true);
    }

    private Action requestLeftStop() {
        return new MoveAction(true, false);
    }

    private Action requestRight() {
        return new MoveAction(false, true);
    }

    private Action requestRightStop() {
        return new MoveAction(false, false);
    }

    private Action jumpRequest() {
        return new JumpAction();
    }


    public class MoveAction extends AbstractAction {
        private boolean left;
        private boolean move;
        public MoveAction(boolean left, boolean move) {
            this.left = left;
            this.move = move;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Auslagern
            //World.getInstance().getPlayers().get(0).move(direction.getX());
            MoveCommand c = new MoveCommand(accessor.getTarget().getID(), left, move);
            // ALSO do the update // TODO fix
            commandQueue.add(c);
        }
    }

    public class JumpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            JumpCommand c = new JumpCommand(accessor.getTarget().getID());
            commandQueue.add(c);
            //World.getInstance().getPlayers().get(0).jump();
        }
    }
}
