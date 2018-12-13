package com.game.input;

import com.game.gameworld.World;
import com.helper.Vector2f;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;

public class InputLogic {
    private JComponent component;
    private Queue<Command> commandQueue;
    private World w;
    public InputLogic(JComponent component) {
        w = World.getInstance();
        commandQueue = new ArrayDeque<>();
        //myKeyListener = new MyKeyListener();
        //component.addKeyListener(myKeyListener);

        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Pressed.up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "Released.up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Pressed.down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "Released.down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Pressed.left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "Released.left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Pressed.right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "Released.right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Jump");


        actionMap.put("Pressed.up", requestUp());
        actionMap.put("Released.up", requestUpStop());
        actionMap.put("Pressed.down", requestDown());
        actionMap.put("Released.down", requestDownStop());
        actionMap.put("Pressed.left", requestLeft());
        actionMap.put("Released.left", requestLeftStop());
        actionMap.put("Pressed.right", requestRight());
        actionMap.put("Released.right", requestRightStop());

        actionMap.put("Jump", jumpRequest());
    }


    private Action requestUp() {
        return new MoveAction(new Vector2f(0f, -1f));
    }

    private Action requestUpStop() {
        return new MoveAction(new Vector2f(0f, 1f));
    }

    private Action requestDown() {
        return new MoveAction(new Vector2f(0f, 1f));
    }

    private Action requestDownStop() {
        return new MoveAction(new Vector2f(0f, -1f));
    }

    private Action requestLeft() {
        return new MoveAction(new Vector2f(-1f, 0f));
    }

    private Action requestLeftStop() {
        return new MoveAction(new Vector2f(1f, 0f));
    }

    private Action requestRight() {
        return new MoveAction(new Vector2f(1f, 0f));
    }

    private Action requestRightStop() {
        return new MoveAction(new Vector2f(-1f, 0f));
    }

    private Action jumpRequest() {
        return new JumpAction();
    }

    public class MoveAction extends AbstractAction {
        private Vector2f direction;
        public MoveAction(Vector2f direction) {
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            World.getInstance().getPlayers().get(0).move(direction.getX());
        }
    }

    public class JumpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            World.getInstance().getPlayers().get(0).jump();
        }
    }
}
