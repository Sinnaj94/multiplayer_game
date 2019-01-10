package com.game.tiles;

import com.helper.Vector2i;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerTilesetFactory {
    // Floor tiles
    private BufferedImage image;
    private BufferedImage[] idleLeftImages;
    private BufferedImage[] idleRightImages;

    private Random r;

    private int tileSize;

    /**
     * TilesetFactory constructor. Returns several Images from a given JSON-Sourcefile
     *
     */
    public PlayerTilesetFactory() {
        idleLeftImages = new BufferedImage[4];
        idleRightImages = new BufferedImage[4];
        try {
            JSONParser jsonParser = new JSONParser();


            JSONObject obj = (JSONObject)jsonParser.parse(new FileReader("res/tilesets/person_tiles.json"));
            image = ImageIO.read(new File((String) obj.get("src")));

            JSONArray currentAnimation = (JSONArray) obj.get("idle_left");
            for(int i = 0; i < currentAnimation.size(); i++) {
                JSONObject current = (JSONObject) currentAnimation.get(i);
                int x = (int)(long)current.get("x");
                int y = (int)(long)current.get("y");
                idleLeftImages[i] = image.getSubimage(x, y, 16, 16);
            }
            currentAnimation = (JSONArray) obj.get("idle_right");
            for(int i = 0; i < currentAnimation.size(); i++) {
                JSONObject current = (JSONObject) currentAnimation.get(i);
                int x = (int)(long)current.get("x");
                int y = (int)(long)current.get("y");
                idleRightImages[i] = image.getSubimage(x, y, 16, 16);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getIdleLeft() {
        return idleLeftImages;
    }

    public BufferedImage[] getIdleRight() {
        return idleRightImages;
    }
}
