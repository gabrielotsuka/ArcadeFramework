package freezeMonsters;

import freezeMonsters.sprite.Monster;
import freezeMonsters.sprite.Woody;
import freezeMonsters.sprite.WoodyRay;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.Random;

import static freezeMonsters.Commons.*;

public class FreezeMonstersBoard extends AbstractBoard {

    private WoodyRay woodyRay = createShot();
    private int rayDirectionX = -1;
    private int rayDirectionY = -1;

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
        Random generator = new Random();
        for (int i=0; i<1; i++) {
            int monsterX = 0;
//            generator.nextInt(BOARD_WIDTH - SPRITE_WIDTH - 1) + 1;
            int monsterY = 0;
//            generator.nextInt(BOARD_HEIGHT - SPRITE_HEIGHT - 29) + 1;
            Monster monster = new Monster(monsterX, monsterY);
            badSprites.add(monster);
        }
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

    private void drawBadSprites(Graphics g) {
        for (BadSprite bad : badSprites) {
            if (bad.isVisible() || bad.isDestroyed()) {
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

    @Override
    protected void doDrawing(Graphics g1) {
        g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(147, 189, 140));
        g.fillRect(0,0,d.width, d.height);

        if (inGame) {
            drawPlayers(g);
            drawBadSprites(g);
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

        // Player
        for (Player player: players)
            player.act();

        // Shot
        if (woodyRay.isVisible()) {

            int shotX = woodyRay.getX();
            int shotY = woodyRay.getY();

            for (BadSprite monster : badSprites) {
                int monsterX = monster.getX();
                int monsterY = monster.getY();

                if (!monster.isDestroyed() && woodyRay.isVisible()) {
                    if (
                            shotX >= (monsterX) &&
                                    shotX <= (monsterX + spaceinvaders.Commons.ALIEN_WIDTH) &&
                                    shotY >= (monsterY) &&
                                    shotY <= (monsterY + spaceinvaders.Commons.ALIEN_HEIGHT)
                    ) {
                        monster.setDying(true);
                        deaths++;
                        woodyRay.die();
                    }
                }
            }

            if (rayDirectionX == 0 && rayDirectionY == 0) {
                rayDirectionX = players.get(0).getDx();
                rayDirectionY = players.get(0).getDy();
            }
            int y = woodyRay.getY(), x = woodyRay.getX();
            y += rayDirectionY;
            x += rayDirectionX;

            if (y < 0 || y > BOARD_HEIGHT || x < 0 || x > BOARD_WIDTH) {
                woodyRay.die();
            } else {
                woodyRay.setY(y);
                woodyRay.setX(x);
            }
        }

        // Monsters
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for (BadSprite bad : badSprites) {
            if(time.getTime() - bad.getLastTimeMoved() <= 1000 && (bad.getNextX() > SPRITE_WIDTH && bad.getNextX() < BOARD_WIDTH-SPRITE_WIDTH && bad.getNextY() > SPRITE_HEIGHT && bad.getNextY() < BOARD_HEIGHT-SPRITE_HEIGHT)) {
                bad.act();
                continue ;
            }
            int dx, dy;
            do {
                Random generate = new Random();
                dx = generate.nextInt(3) -1; // [-1, 1];
                dy = generate.nextInt(3) -1; // [-1, 1];
            } while (!(bad.getNextX(dx) > SPRITE_WIDTH && bad.getNextX(dx) < BOARD_WIDTH-SPRITE_WIDTH && bad.getNextY(dy) > SPRITE_HEIGHT && bad.getNextY(dy) < BOARD_HEIGHT-SPRITE_HEIGHT));

            bad.setLastTimeMoved(time.getTime());
            bad.act(dx, dy);
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
                    rayDirectionX = player.getDx();
                    rayDirectionY = player.getDy();
                    woodyRay = new WoodyRay(x, y);
                }
            }
        }
    }

    @Override
    protected void gameOver(Graphics2D g) {

    }
}
