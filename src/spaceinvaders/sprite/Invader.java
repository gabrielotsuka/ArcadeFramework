package spaceinvaders.sprite;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

public class Invader extends BadnessBoxSprite {

    private InvaderShot invaderShot;

    public Invader(int x, int y) {
        initBomber(x, y);
    }

    private void initBomber(int x, int y) {
        this.x = x;
        this.y = y;

        invaderShot = new InvaderShot(x, y);

        String alienImg = "src/images/alien.png";
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }

    public InvaderShot getBomb() {
        return invaderShot;
    }

	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
		aBomb.add(invaderShot);
		return aBomb;
	}
}
