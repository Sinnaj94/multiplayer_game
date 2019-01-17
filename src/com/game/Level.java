package com.game;

import com.game.gameworld.Chunk;
import com.game.gameworld.World;
import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.game.tiles.OldTilesetFactory;
import com.helper.BoundingBox;

import java.awt.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Level implements Renderable, Collideable {
    private String levelPath;
    //private Floor floor;
    private OldTilesetFactory t;
    private Map<Integer, Chunk> chunkMap;
    private Object intersectsObject;
    public Level(String tilesetPath) {
        // TODO: Get Level from String
        t = new OldTilesetFactory(getClass().getClassLoader().getResourceAsStream(tilesetPath));

        chunkMap = new HashMap<>();
        chunkMap.put(0, new Chunk(0, t));
        this.levelPath = levelPath;
        build();
    }

    public void build() {
        for (Chunk c : chunkMap.values()) {
            c.build();
        }
    }

    @Override
    public void paint(Graphics g) {
        for (Chunk c : chunkMap.values()) {
            c.paint(g);
        }
    }

    @Override
    public boolean intersects(BoundingBox other) {
        // TODO
        int roundedX = (int) Math.floor(other.getX() / World.CHUNK_SIZE);
        int roundedRight = (int) Math.floor((other.getX() + other.getWidth()) / World.CHUNK_SIZE);
        int right = (int) Math.floor((other.getX() + World.CHUNK_SIZE) / World.CHUNK_SIZE);
        int left = (int) Math.floor((other.getX() - World.CHUNK_SIZE) / World.CHUNK_SIZE);
        if(roundedX == roundedRight) {
            return chunkMap.get(roundedX).intersects(other);
        }
        return chunkMap.get(roundedX).intersects(other) || chunkMap.get(roundedRight).intersects(other);
    }

    /*@Override
    public Object intersectsObject(BoundingBox other) {
        int roundedX = (int) Math.floor(other.getX() / World.CHUNK_SIZE);
        if(intersects(other)) {
            return chunkMap.get(roundedX);
        }
        return null;
    }*/

    private void updateMap(int roundedX) {
        int left = roundedX -1;
        int right = roundedX + 1;
        if (!chunkMap.containsKey(right)) {
            Chunk c = new Chunk(right, t);
            c.build();
            chunkMap.put(right, c);
        }
        if (!chunkMap.containsKey(left)) {
            Chunk c = new Chunk(left, t);
            c.build();
            chunkMap.put(left, c);
            //System.out.println(chunkMap.get(left) == chunkMap.get(roundedX));
        }
        if (!chunkMap.containsKey(roundedX)) {
            Chunk c = new Chunk(roundedX, t);
            c.build();
            chunkMap.put(roundedX, c);
        }
    }

    @Override
    public BoundingBox createIntersection(BoundingBox other) {
        int roundedX = (int) Math.floor(other.getX() / World.CHUNK_SIZE);
        int roundedRight = (int) Math.floor((other.getX() + other.getWidth()) / World.CHUNK_SIZE);
        updateMap(roundedX);
        if(chunkMap.get(roundedX).intersects(other)) {
            return chunkMap.get(roundedX).createIntersection(other);
        }
        return chunkMap.get(roundedRight).createIntersection(other);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }
}
