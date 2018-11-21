package game;

import game.generics.GameObject;
import game.generics.Renderable;
import game.tiles.FloorTile;
import game.tiles.Tile;
import game.tiles.TilesetFactory;
import helper.Vector2f;
import helper.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level implements Renderable {
    private String levelPath;
    private List<GameObject> floor;
    private TilesetFactory t;
    private int tileSize;
    public Level(String levelPath, String tilesetImageSrc) {
        // TODO: Get Level from String
        floor = new ArrayList<>();
        this.levelPath = levelPath;
        t = new TilesetFactory(tilesetImageSrc);
        tileSize = t.getTileSize();
        buildLevel();
    }

    public void buildLevel() {
        //TODO: Build from the Charset thing
        for(int x = 0; x < 20; x++) {
            for(int y = 0; y < 20; y++) {
                // Implicitely add a new tile
                FloorTile c = new FloorTile(t.getRandomFloorTile().getResultImg());
                c.setPosition(new Vector2f((float)(tileSize * x), (float)(tileSize * y)));
                floor.add(c);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        for(GameObject ga:floor) {
            ga.paint(g);
        }
    }
}
