package com.game.tiles;

import com.game.generics.Updateable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceSingleton implements Updateable {
    private static ResourceSingleton instance;

    public PlayerTilesetFactory getPlayers() {
        return players;
    }

    public PlayerTilesetFactory getEnemies() {
        return enemies;
    }

    private PlayerTilesetFactory players;
    private PlayerTilesetFactory enemies;

    public PlayerTilesetFactory getDamage() {
        return damage;
    }

    private PlayerTilesetFactory damage;
    public PlayerTilesetFactory getDead() {
        return dead;
    }

    private PlayerTilesetFactory dead;

    public BufferedImage getHeart() {
        return heart;
    }

    private BufferedImage heart;
    public static ResourceSingleton getInstance() {
        if(instance == null) {
            instance = new ResourceSingleton();
        }
        return instance;
    }

    public ResourceSingleton() {
        players = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/person_tiles.json"));
        enemies = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/enemy_tiles.json"));
        dead = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/dead_tiles.json"));
        damage = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/damage_tiles.json"));
        try {
            heart = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        players.update();
        enemies.update();
        dead.update();
        damage.update();
    }
}
