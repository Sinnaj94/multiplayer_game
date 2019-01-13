package com.network.server;

import com.game.event.MoveGameObjectEvent;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.network.common.EventMessage;
import com.network.common.Manager;

public class ServerTickThread implements Runnable {
    private Manager m;
    private World.Accessor accessor;
    public ServerTickThread(Manager m) {
        accessor = World.getInstance().getAccessor();
        this.m = m;
    }

    @Override
    public void run() {
        while(!m.inactive) {
            try {
                tick();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        for(GameObject g:accessor.get()) {
            m.send(new EventMessage(new MoveGameObjectEvent(g)));
        }
    }
}
