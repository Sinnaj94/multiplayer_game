package com.game.ai;

import com.game.event.player.JumpEvent;
import com.game.event.player.MoveEvent;
import com.game.gameworld.*;

import java.util.List;

/**
 * Thread for the AI
 */
public class AIThread {
    private volatile boolean running=true;
    private int id;
    private int tries;
    public AIThread(int id, World.Accessor accessor) {
        // Assign the Current id
        this.id = id;
        new Thread(() -> {
            try {
                while(running) {
                    // Sleep for 200 Milliseconds
                    Thread.sleep(200);
                    try {
                        AIPlayer a = (AIPlayer)accessor.get(id);
                        a.think();
                    } catch(NullPointerException e) {
                        // It will try 5 times to get the Object
                        tries++;
                        if(tries > 4) {
                            running = false;
                        } else {

                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
