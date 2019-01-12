package com.game.tiles;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TilesetFactory {
    // Floor tiles
    private BufferedImage image;

    public Map<String, BufferedImage[]> getAnimationMap() {
        return animationMap;
    }

    // Storing in the Map
    private Map<String, BufferedImage[]> animationMap;
    private Random r;

    private int tileSize;

    /**
     * OldTilesetFactory constructor. Returns several Images from a given JSON-Sourcefile
     *
     */
    public TilesetFactory(String jsonSource) {
        try {
            // Storing them in a HashMap
            animationMap = new HashMap<>();
            JSONParser jsonParser = new JSONParser();

            JSONObject obj = (JSONObject)jsonParser.parse(new FileReader(jsonSource));
            image = ImageIO.read(new File((String) obj.get("src")));
            int playerSize = (int)(long)obj.get("object_size");
            JSONObject animations = (JSONObject)obj.get("animations");
            Iterator iterator = animations.keySet().iterator();

            while(iterator.hasNext()) {
                String key = (String)(iterator.next());
                JSONArray current = (JSONArray) animations.get(key);
                BufferedImage[] buffered = new BufferedImage[current.size()];

                for(int i = 0; i < current.size(); i++) {
                    // {"x":x, "y":y}
                    JSONObject positions = (JSONObject) current.get(i);
                    int x = (int)(long)positions.get("x");
                    int y = (int)(long)positions.get("y");
                    buffered[i] = image.getSubimage(x, y, playerSize, playerSize);
                }
                animationMap.put(key, buffered);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public class PlayerTilesetFactory {

    }

}
