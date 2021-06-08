package freezeMonsters;

import freezeMonsters.sprite.Woody;
import spriteframework.AbstractBoard;
import spriteframework.sprite.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class FreezeMonstersBoard extends AbstractBoard {

    public FreezeMonstersBoard() {
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
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
            g.drawLine(0, spaceinvaders.Commons.GROUND, spaceinvaders.Commons.BOARD_WIDTH, spaceinvaders.Commons.GROUND);
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

    }

    @Override
    protected void processOtherSprites(Player player, KeyEvent e) {

    }

    @Override
    protected void gameOver(Graphics2D g) {

    }
}
