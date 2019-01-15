package com.game.gameworld;

import com.game.ai.Sensors;
import com.game.event.player.JumpEvent;
import com.game.event.player.MoveEvent;
import com.game.factories.PlayerFactory;
import com.helper.BoundingBox;
import com.helper.Vector2f;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.util.List;


public class AIPlayer extends Player {
    private Sensors sensors;
    private World.Accessor accessor;
    public AIPlayer(String username) {
        super(username);
        buildAttributes();
    }

    public AIPlayer(String username, Vector2f position) {
        super(username, position);
        buildAttributes();
    }

    public AIPlayer(BoundingBox prototype, String username) {
        super(prototype, username);
        buildAttributes();
    }

    public AIPlayer(BoundingBox prototype, int id, String username) {
        super(prototype, id, username);
        buildAttributes();
    }

    private void buildAttributes() {
        sensors = new Sensors(getBoundingBox());
        accessor = World.getInstance().getAccessor();
    }

    private Player determineFollowedPlayer() {
        List<Player> players = accessor.getPlayers();
        double smallestDistance = -1;
        Player _return = null;
        for(Player p: players) {
            double currentDistance = distanceTo(p);
            if(currentDistance < smallestDistance || smallestDistance == -1) {
                smallestDistance = currentDistance;
                _return = p;
            }
        }
        return _return;
    }

    @Override
    public GameObjectType getGameObjectType() {
        return GameObjectType.AIPLAYER;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(sensors!=null && World.getInstance().DEBUG_DRAW) {
            sensors.paint(g);
        }
    }

    @Override
    public void update() {
        super.update();
        sensors.update();
    }

    public void think() {
        Player p = determineFollowedPlayer();
        if(p!=null) {
            if(p.getPosition().getX() > getPosition().getX() + 16) {
                smartFollowRight();
            } else if(p.getPosition().getX() < getPosition().getX() - 16) {
                smartFollowLeft();

            } else {
                stop();
            }
        } else {

        }
    }

    private void stop() {
        accessor.addEvent(new MoveEvent(getID(), true, false));
        accessor.addEvent(new MoveEvent(getID(), false, false));
    }

    private void smartFollowLeft() {
        if(sensors.is(Sensors.SensorPosition.WALLLEFT, accessor.getLevel()) || !sensors.is(Sensors.SensorPosition.ABYSSLEFT, accessor.getLevel())) {
            accessor.addEvent(new JumpEvent(getID()));
        }
        accessor.addEvent(new MoveEvent(getID(), false, false));
        accessor.addEvent(new MoveEvent(getID(), true, true));
    }

    private void smartFollowRight() {
        if(sensors.is(Sensors.SensorPosition.WALLRIGHT, accessor.getLevel()) || !sensors.is(Sensors.SensorPosition.ABYSSRIGHT, accessor.getLevel())) {
            accessor.addEvent(new JumpEvent(getID()));
        }
        accessor.addEvent(new MoveEvent(getID(), true, false));
        accessor.addEvent(new MoveEvent(getID(), false, true));
    }
}
