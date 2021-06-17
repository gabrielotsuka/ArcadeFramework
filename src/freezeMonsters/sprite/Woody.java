package freezeMonsters.sprite;

import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static freezeMonsters.Commons.*;

public class Woody extends Player {
    @Override
    public void loadImage() {
        ImageIcon ii = new ImageIcon("src/images/2woody.png");
        setImage(ii.getImage());
    }

    @Override
    public void act() {
        x += dx;
        y += dy;
        if (x <= 2) {
            x = 2;
        }
        if (x >= SPRITE_RIGHT_BORDER) {
            x = SPRITE_RIGHT_BORDER;
        }
        if (y <= 2) {
            y = 2;
        }
        if (y >= SPRITE_DOWN_BORDER) {
            y = SPRITE_DOWN_BORDER;
        }
    }

    @Override
    public void processPressAction(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    @Override
    public void processReleaseAction(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
