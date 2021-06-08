package freezeMonsters.sprite;

import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static freezeMonsters.Commons.*;

public class Woody extends Player {
    @Override
    public void loadImage() {
        ImageIcon ii = new ImageIcon("src/images/woody.png");
        Image scaledImage = ii.getImage().getScaledInstance(WOODY_WIDTH, WOODY_HEIGHT, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        x += dx;
        y += dy;
        if (x <= 2) {
            x = 2;
        }
        if (x >= BOARD_WIDTH - WOODY_WIDTH) {
            x = BOARD_WIDTH - WOODY_WIDTH;
        }
        if (y <= 2) {
            y = 2;
        }
        if (y >= BOARD_HEIGHT - WOODY_HEIGHT - 35) {
            y = BOARD_HEIGHT - WOODY_HEIGHT - 35;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
    public void keyReleased(KeyEvent e) {
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
