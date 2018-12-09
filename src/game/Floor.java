package game;

import game.gameworld.GameObject;
import game.gameworld.World;
import game.tiles.FloorTile;
import game.tiles.TilesetFactory;
import helper.BoundingBox;
import helper.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Floor extends GameObject {
    private List<GameObject> gameObjectList;
    private TilesetFactory tilesetFactory;
    private int tileSize;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    public Floor(TilesetFactory tilesetFactory) {
        minX = 0;
        maxX = 20;
        minY = 10;
        maxY = 20;
        gameObjectList = new ArrayList<>();
        this.tilesetFactory = tilesetFactory;
        tileSize = tilesetFactory.getTileSize();
    }

    public void build() {
        gameObjectList.clear();
        setPosition(new Vector2f((float)minX * 16, (float)minY * 16));
        setSize(new Vector2f((float)(maxX - minX) * 16, (float)(maxY - minY) * 16));
        for(int x = minX; x < maxX; x++) {
            for(int y = minY; y < maxY; y++) {
                // Implicitely add a new tile
                FloorTile c = new FloorTile(tilesetFactory.getRandomFloorTile().getResultImg());
                c.setPosition(new Vector2f((float)(tileSize * x), (float)(tileSize * y)));
                gameObjectList.add(c);
            }
        }
    }

    // TODO: improve
    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(new Vector2f((float)minX*tileSize, (float) minY*tileSize),
                               new Vector2f((float)maxX * tileSize - minX * tileSize, (float)maxY * tileSize - minY * tileSize));
    }

    @Override
    public void paint(Graphics g) {
        for(GameObject ga:gameObjectList) {
            if(World.getInstance().DEBUG_DRAW) {
                super.paint(g);
            } else {
                ga.paint(g);
            }
        }
    }
}
