package com.game.render;
import com.game.input.MouseLogic;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JFrame {
    public RenderPanel getRenderPanel() {
        return renderPanel;
    }

    private RenderPanel renderPanel;
    private JPanel customPanel;
    private JButton zoomIn;
    private JButton zoomOut;
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
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void addComponent(JComponent component) {
        customPanel.add(component, BorderLayout.PAGE_END);
        customPanel.repaint();
        customPanel.revalidate();
        this.pack();
    }

    public void addMapControl() {
        zoomIn = new JButton("+");
        zoomIn.addActionListener(e -> {
            renderPanel.zoom(.1f);
        });
        zoomOut = new JButton("-");
        zoomOut.addActionListener(e -> {
            renderPanel.zoom(-.1f);
        });
        customPanel.add(zoomIn);
        customPanel.add(zoomOut);
        customPanel.repaint();
        customPanel.revalidate();
        this.pack();
    }
}
