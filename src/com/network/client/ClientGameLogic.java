package com.network.client;

import com.game.gameworld.GameObject;
import com.game.gameworld.Renderer;
import com.game.gameworld.World;
import com.game.input.Command;
import com.game.input.InputLogic;

public class ClientGameLogic implements Runnable{
    World w = World.getInstance();
    // TODO: UPDATE_RATE DYNAMICALLY
    private final int CLIENT_UPDATE_RATE = 10;
    private Client client;
    private long lastTime;
    public static double averageUpdateTime;
    private static final World world = World.getInstance();
    private Renderer renderer;
    private InputLogic inputLogic;
    public ClientGameLogic() {
        client = new Client(6060);
        renderer = new Renderer();
        new Thread(renderer).start();
        inputLogic = new InputLogic(renderer.getPanel());
    }

    @Override
    public void run() {
        while(true) {
            synchronized (world) {
                if (System.currentTimeMillis() - lastTime > CLIENT_UPDATE_RATE) {
                    while(!inputLogic.getCommandQueue().isEmpty()) {
                        Command command = inputLogic.getCommandQueue().poll();
                        if(command!=null) {
                            command.execute();
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
        ClientGameLogic clientGameLogic = new ClientGameLogic();
        new Thread(clientGameLogic).start();
    }
}
