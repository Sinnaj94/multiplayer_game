package game;

import game.generics.Renderable;
import game.generics.Collideable;
import game.tiles.TilesetFactory;

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
    public boolean intersects(Collideable collideable) {
        return boundingBox().intersects(collideable.boundingBox());
    }

    @Override
    public Rectangle boundingBox() {
        return floor.boundingBox();
    }
}
