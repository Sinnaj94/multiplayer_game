package game.gameworld;

import game.input.MoveCommand;
import helper.Vector2f;
import helper.Vector2i;
import network.common.MoveMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Renderer implements Runnable {
    private World world;
    private Vector2i size;
    private JFrame jFrame;
    private Canvas canvas;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2i SIZE = new Vector2i(600, 400);
    final long UPDATE_RATE = 10;
    private long lastTime;
    // TODO: Manager auslagern
    public Renderer() {
        this.running = true;
        world = World.getInstance();
        // TODO: Umschreiben
        MoveCommand c = new MoveCommand();
        jFrame = new JFrame("Game");
        JPanel panel = (JPanel)jFrame.getContentPane();
        panel.setPreferredSize(new Dimension(SIZE.getX(), SIZE.getY()));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0,0, SIZE.getX(), SIZE.getY());
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        // TODO

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.setFocusable(true);
        canvas.requestFocus();
        /*InputMap inputMap = panel.getInputMap();
        ActionMap actionMap = panel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Pressed.up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "Released.up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "Pressed.down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "Released.down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "Pressed.left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "Released.left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Pressed.right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "Released.right");


        actionMap.put("Pressed.up", requestUp());
        actionMap.put("Released.up", requestUpStop());
        actionMap.put("Pressed.down", requestDown());
        actionMap.put("Released.down", requestDownStop());
        actionMap.put("Pressed.left", requestLeft());
        actionMap.put("Released.left", requestLeftStop());
        actionMap.put("Pressed.right", requestRight());
        actionMap.put("Released.right", requestRightStop());*/
    }
/*
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

    public class MoveAction extends AbstractAction {
        private Vector2f direction;
        public MoveAction(Vector2f direction) {
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            m.send(new MoveMessage(direction));
        }
    }
*/
    /**
     * Thread for rendering with desired FPS
     */
    @Override
    public void run() {
        while(running) {
            synchronized (World.getInstance()) {
                if(System.currentTimeMillis() - lastTime > UPDATE_RATE) {
                    try {
                        world.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
     * @param g Graphics object
     */
    private void render(Graphics g) {
        // Render the world.
        world.getLevel().paint(g);
        // After that render the GameObjects.
        for(GameObject gameObject:world.getGameObjects()) {
            gameObject.paint(g);
        }
    }
}
