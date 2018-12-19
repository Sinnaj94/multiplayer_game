package com.game.gameworld;

import com.game.generics.Collideable;
import com.game.tiles.FloorTile;
import com.game.tiles.TilesetFactory;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk extends GameObject {
    private List<GameObject> objects;
    Random r;

    public Chunk() {
        objects = new ArrayList<>();
    }

    public void build() {
        TilesetFactory t = new TilesetFactory("res/tilesets/forest_tiles.json");

        objects.clear();
        r = new Random();
        int size = World.TILE_SIZE;
        for(int x = 0; x < World.CHUNK_SIZE; x++) {
            for(int y = 0; y < World.CHUNK_SIZE; y++) {
                if(r.nextBoolean()) {
                    GameObject g = new FloorTile(t.getRandomFloorTile().getResultImg());
                    g.setPosition(size*x, size*y);
                    g.setSize(size, size);
                    objects.add(g);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        for(GameObject _g:objects) {
            _g.paint(g);
        }
    }

    @Override
    public boolean intersects(BoundingBox collideable) {
        for(GameObject c:objects) {
            if(c.intersects(collideable)) {
                return true;
            }
        }
        return false;
    }
}
