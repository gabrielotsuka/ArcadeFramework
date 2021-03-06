package spaceinvaders.sprite;

import spaceinvaders.Commons;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Defender extends Player {

    public void loadImage () {
        ImageIcon ii = new ImageIcon("src/images/defender.png");
        setImage(ii.getImage());
    }

    public void act() {
        x += dx;
        if (x <= 2) {
            x = 2;
        }
        if (x >= Commons.BOARD_WIDTH - imageWidth) {
            x = Commons.BOARD_WIDTH - imageWidth;
        }
    }

    public void processPressAction(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void processReleaseAction(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
