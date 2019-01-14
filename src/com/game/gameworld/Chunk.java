package com.game.gameworld;

import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.game.tiles.FloorTile;
import com.game.tiles.Tile;
import com.game.tiles.OldTilesetFactory;
import com.helper.BoundingBox;
import com.helper.SimplexNoise;
import com.helper.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk implements Collideable, Renderable {
    private List<Tile> tiles;
    private Tile[][] tilesArray;
    Random r;
    private List<BoundingBox> collisionGroup;

    public int getId() {
        return id;
    }

    private int id;

    public Chunk(int id) {
        this.id = id;
        tiles = new ArrayList<>();
        tilesArray = new Tile[World.CHUNK_TILES][World.CHUNK_TILES];
        r = new Random(id);
    }

    public void build() {
        OldTilesetFactory t = new OldTilesetFactory("res/tilesets/forest_tiles.json");
        int size = World.TILE_SIZE;
        SimplexNoise s = new SimplexNoise(0);
        for (int x = 0; x < World.CHUNK_TILES; x++) {
            float n = s.noise(x, id);
            for (int y = 0; y < World.CHUNK_TILES; y++) {
                if (y > n * World.CHUNK_TILES) {
                    Tile g = new FloorTile(t.getRandomFloorTile().getResultImg());
                    g.setPosition(size * x + id * World.CHUNK_TILES * size, size * y);
                    g.setSize(size, size);
                    tilesArray[x][y] = g;
                    /*Tile g = new FloorTile(t.getRandomFloorTile().getResultImg());
                    g.setPosition(size * x + id * World.CHUNK_TILES * size, size * y);
                    g.setSize(size, size);
                    tiles.add(g);*/
                }
            }
        }
        collisionGroup = buildCollisionGroup();
    }

    @Override
    public void paint(Graphics g) {
        if(World.getInstance().DEBUG_DRAW) {
            for(BoundingBox b: collisionGroup) {
                b.paint(g);
            }
        } else  {
            for(int y = 0; y < tilesArray.length; y++) {
                for(int x = 0; x < tilesArray[y].length; x++) {
                    if(tilesArray[x][y] != null) {
                        tilesArray[x][y].paint(g);
                    }
                }
            }
        }
    }

    @Override
    public boolean intersects(BoundingBox collideable) {
        for(BoundingBox c : collisionGroup) {
            if(c.intersects(collideable)) {
                return true;
            }
        }
        return false;
    }

    private List<BoundingBox> buildCollisionGroup() {
        int x = 0;
        List<BoundingBox> collideables = new ArrayList<>();
        while(x < tilesArray.length) {
            BoundingBox current = new BoundingBox(-1,-1,-1,-1);
            for (int y = 0; y < tilesArray.length; y++) {
                BoundingBox a = tilesArray[x][y];
                if(a != null) {
                    if(current.top() > a.top() || current.top() == -1) {
                        current.setPosition(a.getPosition().getX(), a.top());
                    }
                    if(current.bottom() < a.bottom() || current.bottom() == -1) {
                        current.setSize(a.getSize().getX(), a.bottom());
                    }
                }
            }
            collideables.add(current);
            x++;
        }
        return collideables;
    }

    @Override
    public BoundingBox createIntersection(BoundingBox collideable) {
        for(BoundingBox b : collisionGroup) {
            if(b.intersects(collideable)) {
                return b.createIntersection(collideable);
            }
        }
        return null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }
}
