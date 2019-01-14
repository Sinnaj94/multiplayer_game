package com.game.ai;

import com.game.event.player.JumpEvent;
import com.game.event.player.MoveEvent;
import com.game.gameworld.GameObject;
import com.game.gameworld.PhysicsObject;
import com.game.gameworld.Player;
import com.game.gameworld.World;

import java.util.List;

public class PlayerAI {
    private int id;
    private World.Accessor accessor;
    private int followed;
    private float tolerance;
    private Sensors sensors;
    public PlayerAI(int id, World.Accessor accessor) {
        this.accessor = accessor;
        this.id = id;
        followed = -1;
        tolerance = 32;
        new Thread(() -> {
            try {
                while(true) {
                    List<Player> objects = accessor.getPlayers();
                    for(Player player:objects) {
                        if(player.getID() != id) {
                            followed = player.getID();
                        }
                    }
                    GameObject follow = accessor.get(followed);
                    GameObject me = accessor.get(id);
                    sensors = new Sensors(accessor.get(id).getBoundingBox());
                    if(follow != null) {
                        if(follow.getPosition().getX() > me.getPosition().getX() + 32) {
                            goLeft();
                            if(((Player)me).getCollisionCache().is(PhysicsObject.Direction.RIGHT)) {
                                accessor.addEvent(new JumpEvent(id));
                            }
                        } else if(follow.getPosition().getX() < me.getPosition().getX() - 32) {
                            goRight();
                            if(((Player)me).getCollisionCache().is(PhysicsObject.Direction.LEFT)) {
                                accessor.addEvent(new JumpEvent(id));
                            }
                        } else {
                            stop();
                        }
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void stop() {
        accessor.addEvent(new MoveEvent(id, true, false));
        accessor.addEvent(new MoveEvent(id, false, false));
    }

    private void goLeft() {
        accessor.addEvent(new MoveEvent(id, true, false));
        accessor.addEvent(new MoveEvent(id, false, true));
    }

    private void goRight() {
        accessor.addEvent(new MoveEvent(id, false, false));
        accessor.addEvent(new MoveEvent(id, true, true));
    }
}
