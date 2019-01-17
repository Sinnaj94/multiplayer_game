package com.network.client;

import com.game.input.MouseLogic;
import com.game.render.Renderer;
import com.game.gameworld.World;
import com.game.event.player.Command;
import com.game.input.InputLogic;

import java.io.IOException;

public class ClientGameLogic implements Runnable {
    World w = World.getInstance();
    // TODO: UPDATE_RATE DYNAMICALLY
    private final int CLIENT_UPDATE_RATE = 40;
    private Client client;
    private long lastTime;
    public static double averageUpdateTime;
    private static final World world = World.getInstance();
    private Renderer renderer;
    private InputLogic inputLogic;
    private final Object token = new Object();
    private MouseLogic mouseLogic;
    public ClientGameLogic(String ip, int port, String name) throws IOException {
        client = new Client(ip, port, name);
        renderer = new Renderer("Client");
        inputLogic = new InputLogic(renderer.getRenderPanel());
        mouseLogic = new MouseLogic(renderer.getRenderPanel());
        renderer.addMouseListener(mouseLogic);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (World.getInstance()) {
                update();
                // flush away the
                //World.getInstance().getAccessor().getSynchronizedEvents().clear();
                World.getInstance().notifyAll();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        while (!inputLogic.getCommandQueue().isEmpty()) {
            Command command = inputLogic.getCommandQueue().poll();
            if (command != null) {
                client.sendCommand(command);
            }
        }
        while (!mouseLogic.getCommandQueue().isEmpty()) {
            Command command = mouseLogic.getCommandQueue().poll();
            if (command != null) {
                client.sendCommand(command);
            }
        }
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


