package com.network.client;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;

public class ClientGameLogic implements Runnable{
    World w = World.getInstance();
    private final int CLIENT_UPDATE_RATE = 40;
    private long lastTime;
    public static double averageUpdateTime;
    private static final World world = World.getInstance();
    public ClientGameLogic() {

    }

    @Override
    public void run() {
        while(true) {
            synchronized (world) {
                if (System.currentTimeMillis() - lastTime > CLIENT_UPDATE_RATE) {
                    update();
                    world.notifyAll();
                    lastTime = System.currentTimeMillis();
                } else {

                }
            }
        }
    }

    public void update() {
        for(GameObject g:world.getStatics()) {
            g.update();
        }
    }
}
