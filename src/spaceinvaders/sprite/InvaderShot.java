package spaceinvaders.sprite;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;

public class InvaderShot extends BadSprite {

    private boolean destroyed;

    public InvaderShot(int x, int y) {
        initInvaderShot(x, y);
    }

    private void initInvaderShot(int x, int y) {
        setDestroyed(true);

        this.x = x;
        this.y = y;

        String invaderShot = "src/images/2invaderShot.png";
        ImageIcon ii = new ImageIcon(invaderShot);
        setImage(ii.getImage());
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
