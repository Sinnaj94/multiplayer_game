package com.game.tiles;

import com.game.generics.Updateable;

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

    }

    public void update() {
        players.update();
        enemies.update();
        dead.update();
        damage.update();
    }
}
