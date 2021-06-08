package spriteframework;

import spaceinvaders.sprite.DefenderShot;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public abstract class AbstractBoard extends JPanel {

    protected Dimension d;
    protected Graphics2D g;

    protected LinkedList<Player> players;
    protected LinkedList<BadSprite> badSprites;
    
    protected int numberPlayers;
    protected boolean inGame = true;
    protected String message = "Game Over";
    protected Timer timer;
    protected int deaths = 0;

    protected abstract Player createPlayer();
    protected abstract void createBadSprites();
    protected abstract void createOtherSprites();
    protected abstract void drawOtherSprites(Graphics g);
    protected abstract void doDrawing(Graphics g);
    protected abstract void update();
    protected abstract void processOtherSprites(Player player, KeyEvent e);
    protected abstract BadSprite createShot();

    protected abstract void gameOver(Graphics2D g);

    public AbstractBoard() {
        initBoard();
    }

    private void initBoard() {
    	addKeyListener(new TAdapter());
    	setFocusable(true);

        createPlayers();
        numberPlayers = 1;
        badSprites = new LinkedList<>();
        createBadSprites();
        createOtherSprites();

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();
    }

    protected void createPlayers() {
		players = new LinkedList<>();
        players.add(createPlayer());
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            for (Player player: players)
                 player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	for (Player player: players) {
                player.keyPressed(e);

                processOtherSprites(player, e);
        	}
        }
    }
}
