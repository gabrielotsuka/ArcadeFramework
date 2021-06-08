package freezeMonsters.sprite;

import freezeMonsters.Commons;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Woody extends Player {
    @Override
    public void loadImage() {
        ImageIcon ii = new ImageIcon("src/images/woody.png");
        Image scaledImage = ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        x += dx;
        y += dy;
        if (x <= 2) {
            x = 2;
        }
        if (x >= Commons.BOARD_WIDTH - 2 * width) {
            x = Commons.BOARD_WIDTH - 2 * width;
        }
        if (y <= 2) {
            y = 2;
        }
        if (y >= Commons.BOARD_HEIGHT - 2 * width) {
            x = Commons.BOARD_HEIGHT - 2 * width;
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
            dy = 2;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = -2;
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
            dx = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dx = 0;
        }
    }
}
