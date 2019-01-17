package com.game.tiles;

import com.helper.Vector2i;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.stream.Stream;

public class OldTilesetFactory {
    // Floor tiles
    private BufferedImage image;
    private FloorTile[] floorTiles;
    private JSONObject jobj;
    private Random r;

    private int tileSize;

    /**
     * OldTilesetFactory constructor. Returns several Images from a given JSON-Sourcefile
     *
     * @param path
     */
    public OldTilesetFactory(InputStream s) {
        // Getting a JSON Parser
        JSONParser jsonParser = new JSONParser();
        try {
            // Parse the file

            Object obj = jsonParser.parse(new InputStreamReader(s));
            jobj = (JSONObject) obj;
            r = new Random();
            // Read the OldTilesetFactory Image

            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream((String) jobj.get("src")));

            // Set tilesize
            tileSize = (int) (long) jobj.get("tilesize");
            // Build the different Tiles
            // TODO: Simplify (this is the same function actually!)
            // (https://stackoverflow.com/questions/49589023/factory-pattern-for-superclass-and-its-subclasses)
            JSONArray floor = (JSONArray) jobj.get("floor");
            floorTiles = new FloorTile[floor.size()];
            for (int i = 0; i < floor.size(); i++) {
                JSONObject current = (JSONObject) floor.get(i);
                Vector2i position = new Vector2i((int) (long) current.get("x"), (int) (long) current.get("y"));
                Vector2i size = new Vector2i((int) (long) current.get("width"), (int) (long) current.get("height"));
                floorTiles[i] = new FloorTile(image, position, size);
            }

            /*JSONArray walls = (JSONArray) jobj.get("walls");
            wallTiles = new WallTile[floor.size()];
            for (int i = 0; i < walls.size(); i++) {
                wallTiles[i] = new WallTile(image, (JSONObject) floor.get(i));
            }*/
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public int getTileSize() {
        return tileSize;
    }

    public FloorTile getRandomFloorTile() {
        return floorTiles[r.nextInt(floorTiles.length)];
    }

    public FloorTile[] getFloorTiles() {
        return floorTiles;
    }
}
