package spriteframework.sprite;

import java.util.LinkedList;

public abstract class BadSprite extends Sprite {
	public LinkedList<BadSprite>  getBadnesses() {
		return null;
	}
	public boolean isDestroyed() {
		return false;
	}

	public void act() {
		moveX(this.dx);
		moveY(this.dy);
	}

	public void act(int directionX) {
		moveX(directionX);
		moveY(this.dy);
	}

	public void act(int directionX, int directionY) {
		moveX(directionX);
		moveY(directionY);
	}
}
