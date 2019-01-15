package com.game.factories;

import com.game.gameworld.GameObject;
import com.helper.Vector2f;

/**
 * Basic Factory
 */
public abstract class GameObjectFactory {
    public abstract GameObject spawn();
    public abstract GameObject spawn(Vector2f position);
}

