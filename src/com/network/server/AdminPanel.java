package com.network.server;

import com.game.ai.PlayerAI;
import com.game.factories.GameObjectFactory;
import com.game.factories.ItemFactory;
import com.game.factories.PlayerFactory;
import com.game.gameworld.GameObject;
import com.game.gameworld.Item;
import com.game.gameworld.Player;
import com.game.gameworld.World;
import com.helper.Vector2f;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class AdminPanel extends JFrame {
    private JPanel panel;
    private World.Accessor accessor;
    public AdminPanel(World.Accessor accessor) {
        super();
        this.accessor = accessor;
        JPanel panel = new JPanel();
        panel.add(new SpawnPanel());
        panel.add(new FollowPanel());
        this.add(panel);
        pack();
        setVisible(true);
    }
    private class SpawnPanel extends JPanel {
        private JTextField xField;
        private JTextField yField;
        private JComboBox<ComboItem> selection;
        private JButton button;
        public SpawnPanel() {
            xField = new JTextField("0");
            yField = new JTextField("0");
            selection = new JComboBox<ComboItem>();
            selection.addItem(new ComboItem("Item", new ItemFactory()));
            selection.addItem(new ComboItem("Player", new PlayerFactory()));

            button = new JButton("Spawn");
            this.add(xField);
            this.add(yField);
            this.add(selection);
            this.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        float x = Float.parseFloat(xField.getText());
                        float y = Float.parseFloat(yField.getText());
                        //accessor.add((ComboItem)selection.getSelectedItem().);
                        GameObject g = ((ComboItem)selection.getSelectedItem()).value.spawn(new Vector2f(x * World.TILE_SIZE, y * World.TILE_SIZE));
                        accessor.add(g);
                        if(g instanceof Player) {
                            new PlayerAI(g.getID(), accessor);
                        }
                    } catch(NumberFormatException parse) {

                    }

                }
            });
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
    }

    private class FollowPanel extends JPanel {
        private JComboBox<ComboItem> comboBox;
        private JButton watchButton;
        public FollowPanel() {
            super();
            comboBox = new JComboBox();
            setupComboBox();
            this.add(comboBox);
            watchButton = new JButton("Set Focus");
            this.add(watchButton);
            comboBox.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    setupComboBox();
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {

                }
            });
            watchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accessor.setTarget(((ComboItem)comboBox.getSelectedItem()).value.getID());
                }
            });
        }

        private void setupComboBox() {
            comboBox.removeAllItems();
            for(GameObject g : accessor.get()) {
                comboBox.addItem(new ComboItem(g.toString(), g));
            }
        }

        class ComboItem {
            private String key;
            private GameObject value;
            public String toString() {
                return key;
            }

            public ComboItem(String key, GameObject value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}