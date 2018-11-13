package game.tiles;

import com.oracle.javafx.jmx.json.JSONReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.util.List;
public class Tileset {
    // Floor tiles
    private BufferedImage image;
    private FloorTile[] floorTileList;

    /**
     * Tileset constructor. Returns several Images from a given JSON-Sourcefile
     * @param path
     */
    public Tileset(String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader(path));
            JSONObject jobj = (JSONObject)obj;
            image = ImageIO.read(new File((String)jobj.get("src")));
            JSONArray tiles = (JSONArray)jobj.get("floor");
            // Build the floor
            floorTileList = new FloorTile[tiles.size()];
            for(int i = 0; i < tiles.size(); i++) {
                floorTileList[i] = new FloorTile(image, (JSONObject)tiles.get(i));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return this.image;
    }

}
