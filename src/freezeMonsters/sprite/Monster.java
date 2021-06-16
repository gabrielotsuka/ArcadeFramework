package freezeMonsters.sprite;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;

import static freezeMonsters.Commons.*;

public class Monster extends BadnessBoxSprite {

    private int monsterId;

    public MonsterShot monsterShot;

    public Monster(int id, int x, int y) {
        initMonster(id, x, y);
    }

    private void initMonster(int id, int x, int y) {
        Random generator = new Random();
        monsterId = generator.nextInt(9)+1;
        this.x = x;
        this.y = y;

        monsterShot = new MonsterShot(x, y);

        ImageIcon ii = new ImageIcon("src/images/monster" + id + ".png");
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

    @Override
    public void die() {
        ImageIcon ii = new ImageIcon("src/images/monster" + monsterId + "bg.png");
        Image scaledImage = ii.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
        setImage(scaledImage);
        setVisible(false);
        setDestroyed(true);
    }
}
