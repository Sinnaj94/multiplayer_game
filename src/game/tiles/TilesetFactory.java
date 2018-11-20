package game.tiles;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TilesetFactory {
    // Floor tiles
    private BufferedImage image;
    private FloorTile[] floorTiles;
    private WallTile[] wallTiles;
    private JSONObject jobj;


    private int tileSize;
    /**
     * TilesetFactory constructor. Returns several Images from a given JSON-Sourcefile
     *
     * @param path
     */
    public TilesetFactory(String path) {
        // Getting a JSON Parser
        JSONParser jsonParser = new JSONParser();
        try {
            // Parse the file
            Object obj = jsonParser.parse(new FileReader(path));
            jobj = (JSONObject) obj;

            // Read the TilesetFactory Image
            image = ImageIO.read(new File((String) jobj.get("src")));

            // Set tilesize
            tileSize = (int)(long)jobj.get("tilesize");
            // Build the different Tiles
            // TODO: Simplify (this is the same function actually!)
            // (https://stackoverflow.com/questions/49589023/factory-pattern-for-superclass-and-its-subclasses)
            JSONArray floor = (JSONArray) jobj.get("floor");
            floorTiles = new FloorTile[floor.size()];
            for (int i = 0; i < floor.size(); i++) {
                floorTiles[i] = new FloorTile(image, (JSONObject) floor.get(i));
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


    public FloorTile[] getFloorTiles() {
        return floorTiles;
    }

    public WallTile[] getWallTiles() {
        return wallTiles;
    }
}
