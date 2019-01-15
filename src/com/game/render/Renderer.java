package com.game.render;
import javax.swing.*;
import java.awt.*;

public class Renderer extends JFrame {
    public RenderPanel getRenderPanel() {
        return renderPanel;
    }

    private RenderPanel renderPanel;
    private JPanel customPanel;

    public Renderer(String windowName) {
        super(windowName);
        customPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(customPanel, BoxLayout.Y_AXIS);
        customPanel.setLayout(boxlayout);
        renderPanel = new RenderPanel();
        new Thread(renderPanel).start();
        customPanel.add(renderPanel);
        this.add(customPanel);
        this.pack();
        this.setVisible(true);
    }

    public void addComponent(JComponent component) {
        customPanel.add(component, BorderLayout.PAGE_END);
        this.pack();
    }
}
