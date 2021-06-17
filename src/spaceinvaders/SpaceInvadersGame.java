package spaceinvaders;

import spriteframework.AbstractBoard;
import spriteframework.GameFrame;

public class SpaceInvadersGame extends GameFrame {

	public SpaceInvadersGame () {
		runGame("Space Invaders", Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
	}

	@Override
	protected  AbstractBoard createBoard() {
		return new SpaceInvadersBoard();
	}

	public static void main(String[] args) {
		new SpaceInvadersGame();
	}
}
