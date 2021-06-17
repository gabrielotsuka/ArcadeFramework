package spriteframework;

import javax.swing.JFrame;

public abstract class GameFrame extends JFrame  {

    protected abstract AbstractBoard createBoard();

    //Template method Pattern
    protected void runGame(String gameName, int gameWidth, int gameHeight) {
		add(createBoard());
		setSize(gameWidth, gameHeight);
		setTitle(gameName);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
