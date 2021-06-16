package spaceinvaders;


import spaceinvaders.sprite.InvaderShot;
import spaceinvaders.sprite.Defender;
import spaceinvaders.sprite.Invader;
import spaceinvaders.sprite.DefenderShot;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

public class SpaceInvadersBoard extends AbstractBoard{

    private DefenderShot defenderShot = createShot();
    private int direction = -1;

    private final String explImg = "src/images/2explosion.png";

    public SpaceInvadersBoard() {
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);
    }

    protected Player createPlayer() {
        return new Defender();
    }

    protected void createBadSprites() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Invader alien = new Invader(
                        Commons.ALIEN_INIT_X + 18 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                badSprites.add(alien);
            }
        }
    }

    protected void createOtherSprites() {
        defenderShot = new DefenderShot();
    }

    private void drawShot(Graphics g) {
        if (defenderShot.isVisible()) {
            g.drawImage(defenderShot.getImage(), defenderShot.getX(), defenderShot.getY(), this);
        }
    }

    protected void drawOtherSprites(Graphics g) {
        drawShot(g);
    }

    protected void processOtherSprites(Player player, KeyEvent e) {
		int x = player.getX();
		int y = player.getY();

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			if (inGame) {
				if (!defenderShot.isVisible()) {
					defenderShot = new DefenderShot(x, y);
				}
			}
		}
	}

    @Override
    protected DefenderShot createShot() {
        return new DefenderShot();
    }

    @Override
    protected void gameOver(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2, Commons.BOARD_WIDTH / 2);
    }

    protected void update() {
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        for (Player player: players) 
        	player.act();

        // shot
        if (defenderShot.isVisible()) {

            int shotX = defenderShot.getX();
            int shotY = defenderShot.getY();

            for (BadSprite alien : badSprites) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && defenderShot.isVisible()) {
                    if (
                        shotX >= (alienX) &&
                        shotX <= (alienX + Commons.ALIEN_WIDTH) &&
                        shotY >= (alienY) &&
                        shotY <= (alienY + Commons.ALIEN_HEIGHT)
                    ) {
                        ImageIcon ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        defenderShot.die();
                    }
                }
            }

            int y = defenderShot.getY();
            y -= 4;

            if (y < 0) {
                defenderShot.die();
            } else {
                defenderShot.setY(y);
            }
        }

        // aliens
        for (BadSprite alien : badSprites) {
            int x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {
                direction = -1;

                Iterator<BadSprite> i1 = badSprites.iterator();

                while (i1.hasNext()) {
                    BadSprite a2 = i1.next();
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {
                direction = 1;

                Iterator<BadSprite> i2 = badSprites.iterator();

                while (i2.hasNext()) {
                    BadSprite a = i2.next();
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {
            BadSprite alien = it.next();

            if (alien.isVisible()) {
                int y = alien.getY();

                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
		Random generator = new Random();

        for (BadSprite alien : badSprites) {
            int shot = generator.nextInt(15);
            InvaderShot invaderShot = ((Invader)alien).getInvaderShot();

            if (shot == Commons.CHANCE && alien.isVisible() && invaderShot.isDestroyed()) {
                invaderShot.setDestroyed(false);
                invaderShot.setX(alien.getX());
                invaderShot.setY(alien.getY());
            }

            int bombX = invaderShot.getX();
            int bombY = invaderShot.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !invaderShot.isDestroyed()) {
                if (
                        bombX >= (playerX) &&
                        bombX <= (playerX + Commons.PLAYER_WIDTH) &&
                        bombY >= (playerY) &&
                        bombY <= (playerY + Commons.PLAYER_HEIGHT)
                ) {
                    ImageIcon ii = new ImageIcon(explImg);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    invaderShot.setDestroyed(true);
                }
            }

            if (!invaderShot.isDestroyed()) {
                invaderShot.setY(invaderShot.getY() + 1);
                if (invaderShot.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
                    invaderShot.setDestroyed(true);
                }
            }
        }
	}

    protected void doDrawing(Graphics g1) {
        g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.black);
        g.fillRect(0,0,d.width, d.height);
        g.setColor(Color.green);

        if (inGame) {
            g.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
            drawBadSprites(g);
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

    private void drawBadSprites(Graphics g) {
        for (BadSprite bad : badSprites) {
            if (bad.isVisible()) {
                g.drawImage(bad.getImage(), bad.getX(), bad.getY(), this);
            }
            if (bad.isDying()) {
                bad.die();
            }
            if (bad.getBadnesses()!= null) {
                for (BadSprite badness: bad.getBadnesses()) {
                    if (!badness.isDestroyed()) {
                        g.drawImage(badness.getImage(), badness.getX(), badness.getY(), this);
                    }
                }
            }
        }
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
}

