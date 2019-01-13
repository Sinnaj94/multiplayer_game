package com.network.server;

import com.game.gameworld.Renderer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerStartScreen extends JFrame {
    JTextField port;
    JButton trigger;
    private Thread serverThread;
    private ServerGameLogic serverGameLogic;
    private JTextArea debug;
    private JCheckBox render;

    public ServerStartScreen() {
        super();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridwidth = 2;
        JLabel title = new JLabel("SERVER");
        title.setFont(new Font("Courier", Font.BOLD, 32));
        gbc.gridy++;
        panel.add(title);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Port:"), gbc);
        port = new JTextField("6060");
        gbc.gridx++;

        panel.add(port, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        trigger = new JButton("Start");
        panel.add(trigger, gbc);
        gbc.gridy++;
        panel.add(new JLabel("Console:"), gbc);
        gbc.gridy++;
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 200;
        gbc.ipadx = 400;
        debug = new JTextArea();
        debug.setFont(new Font("Andale Mono", Font.BOLD, 12));
        JScrollPane p = new JScrollPane(debug);
        render = new JCheckBox("Render");

        debug.setSize(400, 300);
        panel.add(p, gbc);
        panel.add(render);
        System.setOut(new PrintStream(new AreaOutputStream(debug)));
        //System.setErr(new PrintStream(new AreaOutputStream(debug, true)));
        trigger.addActionListener(e -> {
            if (serverGameLogic == null) {
                int _port = Integer.parseInt(port.getText());
                System.out.println(String.format("Attempting to start Server. (Port %d)", _port));
                try {
                    System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
                } catch (UnknownHostException e1) {
                    System.err.println("Could not determine IP.");
                }
                try {
                    serverGameLogic = new ServerGameLogic(_port);
                    serverThread = new Thread(serverGameLogic);
                    if(render.isSelected()) {
                        new Thread(new Renderer("Test")).start();
                    }
                    serverThread.start();
                    trigger.setEnabled(false);
                } catch (IOException e1) {
                    if (e1 instanceof BindException) {
                        System.out.println(e1.getMessage());
                    }
                }

            }

        });
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.add(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ServerStartScreen s = new ServerStartScreen();
        s.pack();
        s.setVisible(true);
        /*ServerGameLogic serverGameLogic = new ServerGameLogic();
        new Thread(serverGameLogic).start();*/
    }
}
