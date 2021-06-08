package spriteframework.sprite;

import spaceinvaders.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Player extends Sprite {

    protected int width;
    protected int height;
    protected String imagePath;

    public abstract void act();
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void loadImage();

    public Player() {
        loadImage();
		getImageDimensions();
		resetState();
    }

    private void resetState() {
        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }
}
