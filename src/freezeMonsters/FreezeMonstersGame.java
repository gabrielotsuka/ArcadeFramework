package freezeMonsters;

import spriteframework.AbstractBoard;
import spriteframework.GameFrame;

public class FreezeMonstersGame extends GameFrame {

    public FreezeMonstersGame() {
        runGame("Freeze Monsters", Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
    }

    @Override
    protected AbstractBoard createBoard() {
        return new FreezeMonstersBoard();
    }

    public static void main(String[] args) {
        new FreezeMonstersGame();
    }
}
