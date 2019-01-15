package com.game.ai;

import com.game.event.player.JumpEvent;
import com.game.event.player.MoveEvent;
import com.game.gameworld.*;

import java.util.List;

public class AIThread {
    private int id;
    private World.Accessor accessor;
    private int followed;
    private float tolerance;
    public AIThread(int id, World.Accessor accessor) {
        this.accessor = accessor;
        this.id = id;
        followed = -1;
        tolerance = 32;
        System.out.println(accessor.get(id));
        new Thread(() -> {
            try {
                while(true) {
                    try {
                        AIPlayer a = (AIPlayer)accessor.get(id);
                        a.think();
                    } catch(NullPointerException e) {
                        System.out.println("AI is not getting the object.");
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
