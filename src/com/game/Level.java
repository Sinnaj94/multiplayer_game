package com.game;

import com.game.gameworld.Chunk;
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
    private Chunk c;
    private Chunk c2;
    public Level(String levelPath, String tilesetImageSrc) {
        // TODO: Get Level from String
        t = new TilesetFactory(tilesetImageSrc);
        chunkMap = new HashMap<>();
        c = new Chunk(0);
        c2 = new Chunk(1);
        this.levelPath = levelPath;
        build();
    }

    public void build() {
        c.build();
        c2.build();
    }

    @Override
    public void paint(Graphics g) {
        c.paint(g);
        c2.paint(g);
    }

    @Override
    public boolean intersects(BoundingBox collideable) {
        // TODO
        return c.intersects(collideable) || c2.intersects(collideable);
        //return floor.intersects(collideable);
    }

    @Override
    public BoundingBox createIntersection(BoundingBox collideable) {
        return c.createIntersection(collideable);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return c.getBoundingBox();
    }
}
