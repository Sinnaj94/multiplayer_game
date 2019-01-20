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

    public PlayerTilesetFactory getZombies() {
        return zombies;
    }

    private PlayerTilesetFactory players;
    private PlayerTilesetFactory zombies;

    public BufferedImage getTitleBackground() {
        return titleBackground;
    }

    private BufferedImage titleBackground;

    public PlayerTilesetFactory getNinjas() {
        return ninjas;
    }

    private PlayerTilesetFactory ninjas;

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
        zombies = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/zombie_tiles.json"));
        dead = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/dead_tiles.json"));
        damage = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/damage_tiles.json"));
        ninjas = new PlayerTilesetFactory(getClass().getClassLoader().getResourceAsStream("tilesets/ninja_tiles.json"));
        try {
            heart = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/heart.png"));
            titleBackground = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/titlebackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        players.update();
        zombies.update();
        dead.update();
        damage.update();
        ninjas.update();
    }
}
