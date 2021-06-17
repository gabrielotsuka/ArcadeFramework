package spaceinvaders;

import spaceinvaders.sprite.Defender;
import spaceinvaders.sprite.DefenderShot;
import spaceinvaders.sprite.Invader;
import spaceinvaders.sprite.InvaderShot;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import static spaceinvaders.Commons.*;

public class SpaceInvadersBoard extends AbstractBoard{
    
    private DefenderShot defenderShot = createBadSprite();
    private final Defender defender;
    private int direction = -1;

    public SpaceInvadersBoard() {
        super(1, BOARD_WIDTH, BOARD_HEIGHT, Color.black);
        this.defender = (Defender) players.get(0);
    }

    protected Player createPlayer() {
        return new Defender();
    }

    protected void createBadnessBoxSprites() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Invader alien = new Invader(
                        ALIEN_INIT_X + 18 * j,
                        ALIEN_INIT_Y + 18 * i
                );
                badnessBoxSprites.add(alien);
            }
        }
    }

    protected void processPlayerShot(Player player, KeyEvent e) {
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
    protected DefenderShot createBadSprite() {
        return new DefenderShot();
    }

    protected void update() {
        if (enemieDeaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        defender.act();
        updateDefenderShot();

        updateInvaders();
        updateInvaderShots();
    }

    private void updateDefenderShot() {
        if (defenderShot.isVisible()) {

            int shotX = defenderShot.getX();
            int shotY = defenderShot.getY();

            for (BadnessBoxSprite invader : badnessBoxSprites) {
                int invaderX = invader.getX();
                int invaderY = invader.getY();

                if (invader.isVisible() && defenderShot.isVisible()) {
                    boolean shotHitInvader =
                            shotX >= (invaderX) &&
                            shotX <= (invaderX + ALIEN_WIDTH) &&
                            shotY >= (invaderY) &&
                            shotY <= (invaderY + ALIEN_HEIGHT);
                    if (shotHitInvader) {
                        ((Invader)invader).setDeathImage();
                        invader.setDying(true);
                        defenderShot.die();
                        enemieDeaths++;
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
    }

    private void updateInvaders() {
        for (BadnessBoxSprite alien : badnessBoxSprites) {
            int x = alien.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;

                for (BadnessBoxSprite a2 : badnessBoxSprites) {
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                for (BadnessBoxSprite a : badnessBoxSprites) {
                    a.setY(a.getY() + GO_DOWN);
                }
            }

            if (alien.isVisible()) {
                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }
    }

    protected void updateInvaderShots() {
        Random generator = new Random();

        for (BadnessBoxSprite alien : badnessBoxSprites) {
            int shot = generator.nextInt(15);
            InvaderShot invaderShot = ((Invader)alien).getInvaderShot();

            if (shot == CHANCE && alien.isVisible() && invaderShot.isDestroyed()) {
                invaderShot.setDestroyed(false);
                invaderShot.setX(alien.getX());
                invaderShot.setY(alien.getY());
            }

            int bombX = invaderShot.getX();
            int bombY = invaderShot.getY();
            int playerX = defender.getX();
            int playerY = defender.getY();

            if (defender.isVisible() && !invaderShot.isDestroyed()) {
                if (
                    bombX >= (playerX) &&
                    bombX <= (playerX + PLAYER_WIDTH) &&
                    bombY >= (playerY) &&
                    bombY <= (playerY + PLAYER_HEIGHT)
                ) {
                    ImageIcon ii = new ImageIcon("src/images/explosion.png");
                    defender.setImage(ii.getImage());
                    defender.setDying(true);
                    invaderShot.setDestroyed(true);
                }
            }

            if (!invaderShot.isDestroyed()) {
                invaderShot.setY(invaderShot.getY() + 1);
                if (invaderShot.getY() >= GROUND - BOMB_HEIGHT) {
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
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
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
        for (BadnessBoxSprite invader : badnessBoxSprites) {
            if (invader.isVisible()) {
                g.drawImage(invader.getImage(), invader.getX(), invader.getY(), this);
            }
            if (invader.isDying()) {
                invader.die();
            }
            if (invader.getBadnesses()!= null) {
                for (BadSprite badness: invader.getBadnesses()) {
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

    protected void drawOtherSprites(Graphics g) {
        if (defenderShot.isVisible()) {
            g.drawImage(defenderShot.getImage(), defenderShot.getX(), defenderShot.getY(), this);
        }
    }

    @Override
    protected void gameOver(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2, BOARD_WIDTH / 2);
    }
}

