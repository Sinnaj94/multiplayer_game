package com.game.ai;

import com.game.event.player.JumpEvent;
import com.game.event.player.MoveEvent;
import com.game.gameworld.*;

import java.util.List;

public class AIThread {
    private volatile boolean running=true;
    private int id;
    private int tries;
    public AIThread(int id, World.Accessor accessor) {
        this.id = id;
        System.out.println(accessor.get(id));
        new Thread(() -> {
            try {
                while(running) {
                    Thread.sleep(200);
                    try {
                        AIPlayer a = (AIPlayer)accessor.get(id);
                        a.think();
                    } catch(NullPointerException e) {
                        tries++;
                        if(tries > 4) {
                            running = false;

                        } else {
                            System.out.println("AI is not getting the object. Trying again.");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
