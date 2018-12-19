package com.game.input;

import com.game.gameworld.World;
import com.helper.Vector2f;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class InputListener implements KeyListener {
    public boolean isKeyLeftDown() {
        return keyLeftDown;
    }

    public boolean isKeyRightDown() {
        return keyRightDown;
    }

    private boolean keyLeftDown;
    private boolean keyRightDown;
    private final int LEFT = 65;
    private final int RIGHT = 68;
    private final int JUMP = 32;
    public InputListener() {
        keyLeftDown = false;
        keyRightDown = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case LEFT:
                keyLeftDown = true;
            case RIGHT:
                keyRightDown = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case LEFT:
                keyLeftDown = false;
            case RIGHT:
                keyRightDown = false;
        }
    }
}
