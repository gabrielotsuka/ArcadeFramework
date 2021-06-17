package spaceinvaders.sprite;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;

public class DefenderShot extends BadSprite {

    public DefenderShot() {}
    public DefenderShot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        String shotImg = "src/images/defenderShot.png";
        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
