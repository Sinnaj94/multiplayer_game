package com.game.gameworld;

import com.game.ai.AIState;
import com.game.ai.Sensors;
import com.game.event.player.JumpEvent;
import com.game.event.player.MoveEvent;
import com.game.event.player.ShootEvent;
import com.game.factories.PlayerFactory;
import com.game.tiles.PlayerTilesetFactory;
import com.helper.BoundingBox;
import com.helper.Vector2f;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.util.List;
import java.util.Random;


public class AIPlayer extends Player {
    private Sensors sensors;
    private World.Accessor accessor;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active;

    public void setAiState(AIState aiState) {
        this.aiState = aiState;
    }

    private AIState aiState;
    public AIPlayer(String username) {
        super(username);
        buildAttributes();
    }

    public AIPlayer(String username, Vector2f position) {
        super(username, position);
        buildAttributes();
    }

    public AIPlayer(BoundingBox prototype, int id, String username) {
        super(prototype, id, username);
        buildAttributes();
    }

    private void buildAttributes() {
        sensors = new Sensors(getBoundingBox());
        accessor = World.getInstance().getAccessor();
        aiState = AIState.FOLLOW;
        setTilesetFactory(new PlayerTilesetFactory("res/tilesets/enemy_tiles.json"));
        setActive(true);
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
        switch(aiState) {
            case FOLLOW:
                follow();
                break;
            case ATTACK:
                attack();
                break;
            case JUMP:
                aiJump();
                break;
            case ATTACKJUMP:
                follow();
                attack();
                break;
        }
    }

    private void follow() {
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

    private void attack() {
        Player p = determineFollowedPlayer();
        if(p!=null) {
            if(((Player)accessor.get(getID())).canShoot()) {
                //accessor.addEvent(new ShootEvent(getID(), new Vector2f(0f, -1f)));
                Vector2f a = (accessor.get(getID())).getMiddle();
                Vector2f b = p.getMiddle();
                Vector2f d = new Vector2f( b.getX() - a.getX(),  b.getY() - a.getY());
                accessor.addEvent(new ShootEvent(getID(), d));
            }
        }
    }

    private void stop() {
        accessor.addEvent(new MoveEvent(getID(), true, false));
        accessor.addEvent(new MoveEvent(getID(), false, false));
    }

    private void smartFollowLeft() {
        if(sensors.is(Sensors.SensorPosition.WALLLEFT, accessor.getLevel()) || !sensors.is(Sensors.SensorPosition.ABYSSLEFT, accessor.getLevel())) {
            aiJump();
        }
        accessor.addEvent(new MoveEvent(getID(), false, false));
        accessor.addEvent(new MoveEvent(getID(), true, true));
    }

    private void smartFollowRight() {
        if(sensors.is(Sensors.SensorPosition.WALLRIGHT, accessor.getLevel()) || !sensors.is(Sensors.SensorPosition.ABYSSRIGHT, accessor.getLevel())) {
            aiJump();
        }
        accessor.addEvent(new MoveEvent(getID(), true, false));
        accessor.addEvent(new MoveEvent(getID(), false, true));
    }

    private void aiJump() {
        if(getCollisionCache().is(Direction.DOWN)) {
            accessor.addEvent(new JumpEvent(getID()));
        }
    }
}
