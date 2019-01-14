package com.network.server;

import com.game.factories.GameObjectFactory;
import com.game.factories.ItemFactory;
import com.game.factories.PlayerFactory;
import com.game.gameworld.GameObject;
import com.game.gameworld.Item;
import com.game.gameworld.Player;
import com.game.gameworld.World;
import com.helper.Vector2f;

import javax.swing.*;
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
                        accessor.add(((ComboItem)selection.getSelectedItem()).value.spawn(new Vector2f(x, y)));
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
}
