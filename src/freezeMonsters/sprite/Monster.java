package freezeMonsters.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;

public class Monster extends BadnessBoxSprite {

    private int monsterId;

    public MonsterShot monsterShot;

    public Monster(int id, int x, int y) {
        initMonster(id, x, y);
    }

    private void initMonster(int id, int x, int y) {
        Random generator = new Random();
        monsterId = id;
        this.x = x;
        this.y = y;

        monsterShot = new MonsterShot(x, y);

        ImageIcon ii = new ImageIcon("src/images/2monster" + id + ".png");
        setImage(ii.getImage());
    }

    public MonsterShot getMonsterShot() {
        return monsterShot;
    }

    public int getMonsterId() {
        return monsterId;
    }

    @Override
    public LinkedList<BadSprite> getBadnesses() {
        LinkedList<BadSprite> aBomb = new LinkedList<>();
        aBomb.add(monsterShot);
        return aBomb;
    }

    @Override
    public void die() {
        ImageIcon ii = new ImageIcon("src/images/2monster" + monsterId + "bg.png");
        setImage(ii.getImage());
        setVisible(false);
        setDestroyed(true);
    }
}
