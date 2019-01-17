package com.main;

import com.network.client.ClientStartScreen;
import com.network.server.ServerStartScreen;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends JFrame {
    private JButton server;
    private JButton client;
    public Main() {
        super();
        JPanel p = new JPanel();
        server = new JButton("Start Server");
        client = new JButton("Start Client");
        p.add(server);
        p.add(client);

        this.add(p);
        this.pack();
        setVisible(true);
        server.addActionListener(e -> {
            ServerStartScreen s = new ServerStartScreen();
            s.pack();
            s.setVisible(true);
            setVisible(false);
        });
        client.addActionListener(e -> {
            ClientStartScreen s = new ClientStartScreen();
            s.pack();
            s.setVisible(true);
            setVisible(false);
        });
    }

    public static void main(String[] args) {
        new Main();
    }
}
