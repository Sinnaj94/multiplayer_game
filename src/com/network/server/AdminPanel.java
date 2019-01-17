package com.network.server;

import com.game.ai.AIThread;
import com.game.event.player.HitPlayerEvent;
import com.game.factories.AIPlayerFactory;
import com.game.factories.GameObjectFactory;
import com.game.factories.ItemFactory;
import com.game.factories.PlayerFactory;
import com.game.gameworld.AIPlayer;
import com.game.gameworld.GameObject;
import com.game.gameworld.Player;
import com.game.gameworld.World;
import com.helper.Vector2f;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JPanel {
    private World.Accessor accessor;
    public AdminPanel(World.Accessor accessor) {
        super();
        this.accessor = accessor;
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.add(new WorldPanel());
        this.add(panel);
        setVisible(true);
    }
    private class WorldPanel extends JPanel {
        private JTextField xField;
        private JTextField yField;
        private JTextField number;
        private JComboBox<ComboItem> selection;
        private JButton button;
        private JComboBox<GameObjectComboItem> jComboBox;
        private JPanel spawnPanel;
        private JPanel changePanel;
        private JButton refresh;
        private JButton remove;
        private JButton watch;
        private JButton hit;
        public WorldPanel() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            changePanel = new JPanel();
            spawnPanel = new JPanel();
            xField = new JTextField("0");
            yField = new JTextField("0");
            number = new JTextField("1");
            selection = new JComboBox<ComboItem>();
            selection.addItem(new ComboItem("Item", new ItemFactory()));
            selection.addItem(new ComboItem("Player", new PlayerFactory()));
            selection.addItem(new ComboItem("AI", new AIPlayerFactory()));

            button = new JButton("Spawn");
            xField.setPreferredSize(new Dimension(40, 20));
            yField.setPreferredSize(new Dimension(40, 20));
            number.setPreferredSize(new Dimension(40, 20));
            spawnPanel.add(new JLabel("X: "));
            spawnPanel.add(xField);
            spawnPanel.add(new JLabel("Y: "));
            spawnPanel.add(yField);
            spawnPanel.add(new JLabel("Count: "));
            spawnPanel.add(number);
            spawnPanel.add(selection);
            spawnPanel.add(button);
            jComboBox = new JComboBox<GameObjectComboItem>();
            buildComboBox();
            refresh = new JButton("Refresh");
            refresh.addActionListener(e -> {
                buildComboBox();
            });

            remove = new JButton("Remove");
            watch = new JButton("Watch");
            remove.addActionListener(e -> {
                GameObjectComboItem c = ((GameObjectComboItem)jComboBox.getSelectedItem());
                if(c != null) {
                    accessor.remove(c.value.getID());
                    jComboBox.removeItemAt(jComboBox.getSelectedIndex());
                }
            });
            watch.addActionListener(e -> {
                GameObjectComboItem c = ((GameObjectComboItem)jComboBox.getSelectedItem());
                if(c != null) {
                    accessor.setTarget(c.value.getID());
                }
            });
            hit = new JButton("Hit");
            hit.addActionListener(e -> {
                GameObjectComboItem c = ((GameObjectComboItem)jComboBox.getSelectedItem());
                if(c!= null) {
                    accessor.addEvent(new HitPlayerEvent(c.value.getID()));
                }
            });
            changePanel.add(refresh);
            changePanel.add(jComboBox);
            changePanel.add(remove);
            changePanel.add(watch);
            changePanel.add(hit);
            this.add(spawnPanel);
            this.add(changePanel);
            button.addActionListener(e -> {
                try {
                    float x = Float.parseFloat(xField.getText());
                    float y = Float.parseFloat(yField.getText());
                    int n = Integer.parseInt(number.getText());
                    //accessor.add((ComboItem)selection.getSelectedItem().);
                    new Thread(() -> {
                        for(int i = 0; i < n; i++ ) {
                            GameObject g = ((ComboItem)selection.getSelectedItem()).value.spawn(new Vector2f(x * World.TILE_SIZE, y * World.TILE_SIZE));
                            accessor.add(g);
                            if(g instanceof AIPlayer) {
                                new AIThread(g.getID(), accessor);
                            }
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }).start();



                    buildComboBox();
                } catch(NumberFormatException nm) {

                }
            });
        }

        private void buildComboBox() {
            jComboBox.removeAllItems();
            for(GameObject g : accessor.get()) {
                jComboBox.addItem(new GameObjectComboItem(g.toString(), g));
            }
        }

        class ComboItem {
            private String key;

            @Override
            public String toString() {
                return key;
            }

            public String getKey() {
                return key;
            }

            public GameObjectFactory getValue() {
                return value;
            }

            private GameObjectFactory value;

            public ComboItem(String key, GameObjectFactory value) {
                this.key = key;
                this.value = value;
            }

        }

        class GameObjectComboItem {
            private String key;
            private GameObject value;

            @Override
            public String toString() {
                return key;
            }

            public String getKey() {
                return key;
            }

            public GameObject getValue() {
                return value;
            }


            public GameObjectComboItem(String key, GameObject value) {
                this.key = key;
                this.value = value;
            }

        }
    }
}
