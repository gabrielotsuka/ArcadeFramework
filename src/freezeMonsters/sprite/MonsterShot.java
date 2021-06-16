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

        ImageIcon ii = new ImageIcon("src/images/2monsterShot.png");
        setImage(ii.getImage());
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
