package spaceinvaders.sprite;

import spaceinvaders.Commons;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Defender extends Player {

    public void loadImage () {
        ImageIcon ii = new ImageIcon("src/images/defender.png");
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
    }

    public void act() {
        x += dx;
        if (x <= 2) {
            x = 2;
        }
        if (x >= Commons.BOARD_WIDTH - 2 * width) {
            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
