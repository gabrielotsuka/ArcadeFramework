package freezeMonsters.sprite;

import spriteframework.sprite.BadSprite;

import javax.swing.*;

public class WoodyRay extends BadSprite {

    public WoodyRay() {}

    public WoodyRay(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        ImageIcon ii = new ImageIcon("src/images/2ray.png");
        setImage(ii.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
