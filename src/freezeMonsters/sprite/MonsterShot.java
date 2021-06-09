package freezeMonsters.sprite;

import spriteframework.sprite.BadSprite;

import javax.swing.*;
import java.awt.*;

import static freezeMonsters.Commons.*;

public class MonsterShot extends BadSprite {

    private boolean destroyed;

    public MonsterShot(int x, int y) {
        initMonsterShot(x, y);
    }

    private void initMonsterShot(int x, int y) {
        setDestroyed(true);

        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon("src/images/monsterShot.png");
        Image scaledImage = ii.getImage().getScaledInstance(SHOT_WIDTH, SHOT_HEIGHT, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
