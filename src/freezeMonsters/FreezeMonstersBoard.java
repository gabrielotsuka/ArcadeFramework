package freezeMonsters;

import freezeMonsters.sprite.Woody;
import freezeMonsters.sprite.WoodyRay;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static freezeMonsters.Commons.*;

public class FreezeMonstersBoard extends AbstractBoard {

    private WoodyRay woodyRay = createShot();

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
    protected WoodyRay createShot() {
        return new WoodyRay();
    }

    @Override
    protected void createOtherSprites() {

    }

    @Override
    protected void drawOtherSprites(Graphics g) {
        drawShot(g);
    }

    private void drawShot(Graphics g) {
        if (woodyRay.isVisible()) {
            g.drawImage(woodyRay.getImage(), woodyRay.getX(), woodyRay.getY(), this);
        }
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
            drawOtherSprites(g);
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

        // shot
        if (woodyRay.isVisible()) {

            int shotX = woodyRay.getX();
            int shotY = woodyRay.getY();

            for (BadSprite alien : badSprites) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && woodyRay.isVisible()) {
                    if (
                            shotX >= (alienX) &&
                                    shotX <= (alienX + spaceinvaders.Commons.ALIEN_WIDTH) &&
                                    shotY >= (alienY) &&
                                    shotY <= (alienY + spaceinvaders.Commons.ALIEN_HEIGHT)
                    ) {
//                        ImageIcon ii = new ImageIcon(explImg);
//                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        woodyRay.die();
                    }
                }
            }

            int y = woodyRay.getY();
            y -= 4;

            if (y < 0) {
                woodyRay.die();
            } else {
                woodyRay.setY(y);
            }
        }
    }

    @Override
    protected void processOtherSprites(Player player, KeyEvent e) {
        int x = player.getX();
        int y = player.getY();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            if (inGame) {
                if (!woodyRay.isVisible()) {
                    woodyRay = new WoodyRay(x, y);
                }
            }
        }
    }

    @Override
    protected void gameOver(Graphics2D g) {

    }
}
