package com.network.server;

import com.game.event.gameobject.MoveGameObjectEvent;
import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.network.common.EventMessage;
import com.network.common.Manager;

import java.util.Iterator;

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
                synchronized (ServerGameLogic.tickToken) {
                    ServerGameLogic.tickToken.wait();
                }
                tick();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        Iterator<GameObject> iterator = accessor.getTicked().iterator();
        while (iterator.hasNext()){
            GameObject g = iterator.next();
            m.send(new EventMessage(new MoveGameObjectEvent(g.getID(), g.getPosition())));
        }
    }
}
