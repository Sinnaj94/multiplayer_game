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

            // Build the different Tiles
            floorTiles = (FloorTile[]) buildTiles("floor");
            wallTiles = (WallTile[]) buildTiles("walls");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public FloorTile[] getFloorTiles() {
        return floorTiles;
    }

    public WallTile[] getWallTiles() {
        return wallTiles;
    }

    private Tile[] buildTiles(String jsonArrayKey) {
        JSONArray jsonArray = (JSONArray) jobj.get(jsonArrayKey);
        Tile[] tiles = new WallTile[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            tiles[i] = new WallTile(image, (JSONObject) jsonArray.get(i));
        }
        return tiles;
    }
}
