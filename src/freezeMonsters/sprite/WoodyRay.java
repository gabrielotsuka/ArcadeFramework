package freezeMonsters.sprite;

import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;

import static freezeMonsters.Commons.*;

public class WoodyRay extends BadSprite {

    public WoodyRay() {}

    public WoodyRay(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        ImageIcon ii = new ImageIcon("src/images/ray.png");
        Image scaledImage = ii.getImage().getScaledInstance(SHOT_WIDTH, SHOT_HEIGHT, Image.SCALE_SMOOTH);
        setImage(scaledImage);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
