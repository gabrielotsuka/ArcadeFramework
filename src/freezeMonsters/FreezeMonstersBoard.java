package freezeMonsters;

import freezeMonsters.sprite.Woody;
import spriteframework.AbstractBoard;
import spriteframework.sprite.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static freezeMonsters.Commons.*;

public class FreezeMonstersBoard extends AbstractBoard {

    public FreezeMonstersBoard() {
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.green);
    }

    @Override
    protected Player createPlayer() {
        return new Woody();
    }

    @Override
    protected void createBadSprites() {

    }

    @Override
    protected void createOtherSprites() {

    }

    @Override
    protected void drawOtherSprites(Graphics g) {

    }

    @Override
    protected void doDrawing(Graphics g1) {
        g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(147, 189, 140));
        g.fillRect(0,0,d.width, d.height);

        if (inGame) {
            drawPlayers(g);
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawPlayers(Graphics g) {
        for (Player player: players) {
            if (player.isVisible()) {
                g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            }

            if (player.isDying()) {
                player.die();
                inGame = false;
            }
        }
    }

    @Override
    protected void update() {
        if (deaths == NUMBER_OF_MONSTERS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        for (Player player: players)
            player.act();
    }

    @Override
    protected void processOtherSprites(Player player, KeyEvent e) {

    }

    @Override
    protected void gameOver(Graphics2D g) {

    }
}
