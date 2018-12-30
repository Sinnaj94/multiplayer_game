package com.network.client;

import com.game.gameworld.Renderer;
import com.game.gameworld.World;
import com.game.input.Command;
import com.game.input.InputLogic;

import java.io.IOException;

public class ClientGameLogic implements Runnable {
    World w = World.getInstance();
    // TODO: UPDATE_RATE DYNAMICALLY
    private final int CLIENT_UPDATE_RATE = 10;
    private Client client;
    private long lastTime;
    public static double averageUpdateTime;
    private static final World world = World.getInstance();
    private Renderer renderer;
    private InputLogic inputLogic;

    public ClientGameLogic(String ip, int port, String name) throws IOException {
        client = new Client(ip, port, name);
        renderer = new Renderer("Client");
        new Thread(renderer).start();
        inputLogic = new InputLogic(renderer.getPanel());
    }

    @Override
    public void run() {
        while (true) {
            synchronized (world) {
                if (System.currentTimeMillis() - lastTime > CLIENT_UPDATE_RATE) {
                    while (!inputLogic.getCommandQueue().isEmpty()) {
                        Command command = inputLogic.getCommandQueue().poll();
                        if (command != null) {
                            client.sendCommand(command);
                        }
                    }
                    update();
                    world.notifyAll();
                    lastTime = System.currentTimeMillis();
                } else {

                }
            }
        }
    }

    public void update() {
        world.update();
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Takes 3 Arguments: IP, PORT, USERNAME");

            System.exit(-1);
        }
        String address = args[0];
        String username = args[2];
        try {
            int port = Integer.parseInt(args[1]);
            try {
                new Thread(new ClientGameLogic(address, port, username)).start();
            } catch (IOException e) {
                System.out.println(String.format("No connection to Server %s:%d possible.", address, port));
                System.exit(1);
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong format.");
            System.exit(1);
        }
    }
}


