package com.game;

import com.game.gameworld.Chunk;
import com.game.gameworld.World;
import com.game.generics.Renderable;
import com.game.generics.Collideable;
import com.game.tiles.TilesetFactory;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Level implements Renderable, Collideable {
    private String levelPath;
    //private Floor floor;
    private TilesetFactory t;
    private Map<Integer, Chunk> chunkMap;
    public Level(String levelPath, String tilesetImageSrc) {
        // TODO: Get Level from String
        t = new TilesetFactory(tilesetImageSrc);
        chunkMap = new HashMap<>();
        chunkMap.put(0, new Chunk(0));
        System.out.println(chunkMap.size());
        this.levelPath = levelPath;
        build();
    }

    public void build() {
        for(Chunk c:chunkMap.values()) {
            c.build();
        }
    }

    @Override
    public void paint(Graphics g) {
        for(Chunk c:chunkMap.values()) {
            c.paint(g);
        }
    }

    @Override
    public boolean intersects(BoundingBox collideable) {
        // TODO
        int roundedX = (int)Math.floor(collideable.getX() / World.CHUNK_SIZE);
        int right = (int)Math.floor((collideable.getX() + World.CHUNK_SIZE)/ World.CHUNK_SIZE);
        int left = (int)Math.floor((collideable.getX() - World.CHUNK_SIZE) / World.CHUNK_SIZE);
        if(!chunkMap.containsKey(right)) {
            Chunk c = new Chunk(right);
            c.build();
            chunkMap.put(right, c);
        }
        if(!chunkMap.containsKey(left)) {
            Chunk c = new Chunk(left);
            c.build();
            chunkMap.put(left, c);
            //System.out.println(chunkMap.get(left) == chunkMap.get(roundedX));
        }
        return chunkMap.get(roundedX).intersects(collideable);
    }

    @Override
    public BoundingBox createIntersection(BoundingBox collideable) {
        return null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }
}
