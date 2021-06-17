package spriteframework.sprite;

import spaceinvaders.Commons;

import java.awt.event.KeyEvent;

public abstract class Player extends Sprite {

    public abstract void act();
    public abstract void processPressAction(KeyEvent e);
    public abstract void processReleaseAction(KeyEvent e);
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
