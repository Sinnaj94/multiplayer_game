package game;

import game.gameworld.World;
import network.common.Manager;

public class ClientGameLogic {
    private World world;
    private Manager manager;
    public ClientGameLogic(Manager manager) {
        this.manager = manager;
        world = World.getInstance();
    }

    private void clientGameLoop() {
        while(true) {

        }
    }
}
