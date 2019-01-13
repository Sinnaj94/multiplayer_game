package com.game.gameworld;

import com.game.generics.Collideable;
import com.game.generics.Renderable;
import com.game.tiles.FloorTile;
import com.game.tiles.Tile;
import com.game.tiles.OldTilesetFactory;
import com.helper.BoundingBox;
import com.helper.SimplexNoise;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk implements Collideable, Renderable {
    private List<Tile> tiles;
    Random r;

    public int getId() {
        return id;
    }

    private int id;

    public Chunk(int id) {
        this.id = id;
        tiles = new ArrayList<>();
        r = new Random(id);
    }

    public void build() {
        OldTilesetFactory t = new OldTilesetFactory("res/tilesets/forest_tiles.json");

        tiles.clear();
        int size = World.TILE_SIZE;
        SimplexNoise s = new SimplexNoise(0);
        for (int x = 0; x < World.CHUNK_TILES; x++) {
            float n = s.noise(x, id);
            for (int y = 0; y < World.CHUNK_TILES; y++) {

                if (y > n * World.CHUNK_TILES) {
                    Tile g = new FloorTile(t.getRandomFloorTile().getResultImg());
                    g.setPosition(size * x + id * World.CHUNK_TILES * size, size * y);
                    g.setSize(size, size);
                    tiles.add(g);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        for (Tile _g : tiles) {
            _g.paint(g);
        }
    }

    @Override
    public boolean intersects(BoundingBox collideable) {
        for (Tile t : tiles) {
            if (t.intersects(collideable)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object intersectsObject(BoundingBox collideable) {
        for (Tile t : tiles) {
            if (t.intersects(collideable)) {
                return t;
            }
        }
        return null;
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
