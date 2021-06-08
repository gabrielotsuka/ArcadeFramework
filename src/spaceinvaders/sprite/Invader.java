package spaceinvaders.sprite;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

public class Invader extends BadnessBoxSprite {

    private Bomb bomb;

    public Invader(int x, int y) {
        initBomber(x, y);
    }

    private void initBomber(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        String alienImg = "src/images/alien.png";
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }

    public Bomb getBomb() {
        return bomb;
    }

	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
		aBomb.add(bomb);
		return aBomb;
	}
}
