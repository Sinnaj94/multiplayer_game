package game;

import game.gameworld.GameObject;
import game.generics.Renderable;
import game.input.Collideable;
import game.tiles.FloorTile;
import game.tiles.TilesetFactory;
import helper.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public boolean collides(Collideable collideable) {
        return boundingBox().intersects(collideable.boundingBox());
    }

    @Override
    public Rectangle boundingBox() {
        return floor.boundingBox();
    }
}
