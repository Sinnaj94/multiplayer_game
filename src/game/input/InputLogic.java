package game.input;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Queue;

public class InputLogic implements Runnable{
    private JComponent component;
    private Queue<Command> commandQueue;

    public InputLogic(JComponent component) {
        commandQueue = new ArrayDeque<>();
        component.addKeyListener(new MyKeyListener());
    }

    @Override
    public void run() {

    }

    private void addCommandToList(Command command) {
        commandQueue.add(command);
    }

    public class MyKeyListener implements KeyListener {
        private final int KEY_UP = 87;
        private final int KEY_DOWN = 83;
        private final int KEY_LEFT = 65;
        private final int KEY_RIGHT = 68;
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO: machen
            switch(e.getKeyCode()) {
                case KEY_UP:
                    break;
                case KEY_DOWN:
                    break;
                case KEY_LEFT:
                    break;
                case KEY_RIGHT:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
