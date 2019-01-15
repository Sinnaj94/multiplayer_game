package com.game.tiles;

import com.game.gameworld.PlayerState;
import com.game.generics.Updateable;

import java.awt.image.BufferedImage;

public class PlayerTilesetFactory extends TilesetFactory implements Updateable {
    private double currentStep;
    private int currentAnimationStep;
    private double animationStep;
    /**
     * OldTilesetFactory constructor. Returns several Images from a given JSON-Sourcefile
     *
     */
    public PlayerTilesetFactory(String resource) {
        super(resource);
        animationStep = 10;
    }


    public BufferedImage getAnimationFrame(PlayerState playerState, boolean facesLeft) {
        BufferedImage[] temp = null;
        switch(playerState) {
            case IDLE:
                if(facesLeft) {
                    return frame(getAnimationMap().get("idle_left"));
                }
                return frame(getAnimationMap().get("idle_right"));
            case MOVING:
                if(facesLeft) {
                    return frame(getAnimationMap().get("walk_left"));
                }
                return frame(getAnimationMap().get("walk_right"));
            case JUMPING:
                if(facesLeft) {
                    return frame(getAnimationMap().get("jump_left"));
                }
                return frame(getAnimationMap().get("jump_right"));
            case FALLING:
                if(facesLeft) {
                    return frame(getAnimationMap().get("fall_left"));
                }
                return frame(getAnimationMap().get("fall_right"));
        }
        return null;
    }

    private BufferedImage frame(BufferedImage[] image) {
        return image[currentAnimationStep % image.length];
    }

    @Override
    public void update() {
        currentStep++;
        if(currentStep % animationStep == 0) {
            currentAnimationStep++;
        }
    }
}
