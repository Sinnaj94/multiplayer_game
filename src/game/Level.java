package game;

import game.gameworld.World;
import game.generics.Renderable;
import game.generics.Collideable;
import game.tiles.TilesetFactory;
import helper.BoundingBox;

import java.awt.*;

public class Level implements Renderable, Collideable {
    private String levelPath;
    private Floor floor;
    private TilesetFactory t;
    public Level(String levelPath, String tilesetImageSrc) {
        // TODO: Get Level from String
        t = new TilesetFactory(tilesetImageSrc);
        floor = new Floor(t);
        this.levelPath = levelPath;
        build();
    }

    public void build() {
        //TODO: Build from the Charset thing
        floor.build();
    }

    public void update() {

    }

    @Override
    public void paint(Graphics g) {
        floor.paint(g);
    }

    @Override
    public boolean intersects(BoundingBox collideable) {
        return getBoundingBox().intersects(collideable);
    }

    @Override
    public BoundingBox createIntersection(BoundingBox collideable) {
        return getBoundingBox().createIntersection(collideable);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return floor.getBoundingBox();
    }
}
