package game;

import game.Input.Command;
import game.Input.MoveCommand;
import game.gameobjects.Player;
import game.tiles.FloorTile;
import game.tiles.TilesetFactory;
import helper.Vector2f;
import helper.Vector2i;
import network.common.Manager;
import network.common.MoveMessage;

import javax.swing.*;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Renderer implements Runnable {
    private World w;
    private Vector2i size;
    private JFrame jFrame;
    private Canvas canvas;
    private volatile boolean running;
    BufferStrategy bufferStrategy;
    final Vector2i SIZE = new Vector2i(600, 400);
    final long D_FPS = 60;
    final long D_DELTA_LOOP = (1000 * 1000 * 1000)/D_FPS;
    private Vector2i direction;
    FloorTile[] a;
    private int tileSize;
    private Manager m;
    private Object token;
    public Renderer(World w, Manager m) {
        this.m = m;
        this.running = true;
        direction = new Vector2i(0, 0);
        this.w = w;
        MoveCommand c = new MoveCommand();
        /*canvas = new JFrame("Game");
        canvas.setSize(size.getX(), size.getY());
        canvas.setVisible(true);
        this.add(canvas);*/
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
        InputMap inputMap = panel.getInputMap();
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
        actionMap.put("Released.right", requestRightStop());
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

    @Override
    public void run() {
        long beginLoopTime;
        long lastUpdateTime;
        long currentUpdateTime = System.nanoTime();
        long endLoopTime;
        long deltaLoop;
        while(running) {
            beginLoopTime = System.nanoTime();
            render();

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;
            if(deltaLoop > D_DELTA_LOOP) {

            } else {
                try {
                    Thread.sleep((D_DELTA_LOOP - deltaLoop)/(600*1000));
                } catch(InterruptedException e) {

                }
            }
        }


    }

    public void setToken(Object token) {
        this.token = token;
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, SIZE.getX(), SIZE.getY());
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    private void render(Graphics g) {
        // Render the world.
        w.getLevel().paint(g);
        // After that render the players.
        for(Player p:w.getPlayers()) {
            p.paint(g);
        }
    }
}
