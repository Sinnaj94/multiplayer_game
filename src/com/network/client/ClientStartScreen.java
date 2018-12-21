package com.network.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientStartScreen extends JFrame {
    private JTextField ipAddress;
    private JTextField port;
    private JButton connectButton;
    public ClientStartScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel title = new JLabel("Client");
        title.setFont(new Font("Courier", Font.BOLD,32));
        panel.add(title);

        panel.add(new JLabel("IP-Adresse:"));
        ipAddress = new JTextField("localhost");
        panel.add(ipAddress);
        port = new JTextField("6060");
        panel.add(port);
        connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> {
            try {
                String ip = ipAddress.getText();

                int myPort = Integer.parseInt(port.getText());
                try {
                    ClientGameLogic c = new ClientGameLogic(ip, myPort);
                    new Thread(c).start();
                    this.setVisible(false);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, String.format("Could not connect to %s:%d", ip, myPort));
                }
            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(this, String.format("Port format wrong."));
            }
        });
        panel.add(connectButton);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(300, 200));
        this.add(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        ClientStartScreen s = new ClientStartScreen();
        s.pack();
        s.setVisible(true);
    }

}
