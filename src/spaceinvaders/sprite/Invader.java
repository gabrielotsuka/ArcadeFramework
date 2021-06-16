package spaceinvaders.sprite;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

public class Invader extends BadnessBoxSprite {

    private InvaderShot invaderShot;

    public Invader(int x, int y) {
        initInvader(x, y);
    }

    private void initInvader(int x, int y) {
        this.x = x;
        this.y = y;

        invaderShot = new InvaderShot(x, y);

        String alienImg = "src/images/2alien.png";
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }

    public InvaderShot getInvaderShot() {
        return invaderShot;
    }

	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<>();
		aBomb.add(invaderShot);
		return aBomb;
	}
}
