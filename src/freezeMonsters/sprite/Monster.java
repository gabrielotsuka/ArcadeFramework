package freezeMonsters.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import static freezeMonsters.Commons.*;

public class Monster extends BadnessBoxSprite {

    private int monsterId;

    public MonsterShot monsterShot;

    public Monster(int x, int y) {
        initMonster(x, y);
    }

    private void initMonster(int x, int y) {
        Random generator = new Random();
        monsterId = generator.nextInt(10)+1;
        this.x = x;
        this.y = y;

        monsterShot = new MonsterShot(x, y);

        ImageIcon ii = new ImageIcon("src/images/monster" + monsterId + ".png");
        Image scaledImage = ii.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
        setImage(scaledImage);
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
}