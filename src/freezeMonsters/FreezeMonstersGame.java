package freezeMonsters;

import spaceinvaders.Commons;
import spriteframework.AbstractBoard;
import spriteframework.MainFrame;

import java.awt.*;

public class FreezeMonstersGame extends MainFrame {

    public FreezeMonstersGame() {
        super("Freeze Monsters");
        setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
    }

    @Override
    protected AbstractBoard createBoard() {
        return new FreezeMonstersBoard();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(FreezeMonstersGame::new);
    }
}
