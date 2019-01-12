package com.game;

import com.game.gameworld.GameObject;
import com.game.gameworld.World;
import com.game.tiles.FloorTile;
import com.game.tiles.OldTilesetFactory;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Floor extends GameObject {
    private List<GameObject> gameObjectList;
    private OldTilesetFactory oldTilesetFactory;
    private int tileSize;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public Floor(OldTilesetFactory oldTilesetFactory) {
        minX = 0;
        maxX = 100;
        minY = 40;
        maxY = 60;
        gameObjectList = new ArrayList<>();
        this.oldTilesetFactory = oldTilesetFactory;
        tileSize = oldTilesetFactory.getTileSize();
    }

    public void build() {
        gameObjectList.clear();
        setPosition((float) minX * 16, (float) minY * 16);
        setSize(new Vector2f((float) (maxX - minX) * 16, (float) (maxY - minY) * 16));
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                // Implicitely add a new tile
                FloorTile c = new FloorTile(oldTilesetFactory.getRandomFloorTile().getResultImg());
                c.setPosition((float) (tileSize * x), (float) (tileSize * y));
                //gameObjectList.add(c);
            }
        }
    }

    // TODO: improve
    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(new Vector2f((float) minX * tileSize, (float) minY * tileSize),
                new Vector2f((float) maxX * tileSize - minX * tileSize, (float) maxY * tileSize - minY * tileSize));
    }

    @Override
    public void paint(Graphics g) {
        for (GameObject ga : gameObjectList) {
            if (World.getInstance().DEBUG_DRAW) {
                super.paint(g);
            } else {
                ga.paint(g);
            }
        }
    }
}
