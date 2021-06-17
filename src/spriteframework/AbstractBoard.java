package spriteframework;

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

    protected LinkedList<Player> players = new LinkedList<>();
    protected LinkedList<BadSprite> badSprites = new LinkedList<>();
    
    protected int numberOfPlayers = 1;
    protected boolean inGame = true;
    protected String message = "Game Over";
    protected Timer timer;
    protected int deaths = 0;

    protected abstract Player createPlayer();
    protected abstract void createBadSprites();
    protected abstract void createOtherSprites();
    protected abstract BadSprite createShot();
    protected abstract void drawOtherSprites(Graphics g);
    protected abstract void doDrawing(Graphics g);
    protected abstract void processPlayerShot(Player player, KeyEvent e);
    protected abstract void update();

    protected abstract void gameOver(Graphics2D g);

    public AbstractBoard(int numberOfPlayers) {
        initBoard(numberOfPlayers);
    }

    private void initBoard(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

        addKeyListener(new TAdapter());
        setFocusable(true);

        createSprites();

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();
    }

    private void createSprites() {
        createPlayers();
        createBadSprites();
        createOtherSprites();
    }

    protected void createPlayers() {
		players.add(createPlayer());
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
                 player.processReleaseAction(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
        	for (Player player: players) {
                player.processPressAction(e);
                processPlayerShot(player, e);
        	}
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
