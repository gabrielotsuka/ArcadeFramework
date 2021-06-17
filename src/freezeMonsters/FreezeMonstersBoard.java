package freezeMonsters;

import freezeMonsters.sprite.Monster;
import freezeMonsters.sprite.MonsterShot;
import freezeMonsters.sprite.Woody;
import freezeMonsters.sprite.WoodyRay;
import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.Random;

import static freezeMonsters.Commons.*;

public class FreezeMonstersBoard extends AbstractBoard {

    private WoodyRay woodyRay = createBadSprite();
    private int rayDirectionX = -1;
    private int rayDirectionY = -1;

    public FreezeMonstersBoard() {
        super(1, BOARD_WIDTH, BOARD_HEIGHT, Color.green);
    }

    @Override
    protected Player createPlayer() {
        return new Woody();
    }

    @Override
    protected void createBadnessBoxSprites() {
        Random generator = new Random();
        for (int i=0; i<NUMBER_OF_MONSTERS_TO_DESTROY; i++) {
            int monsterX = generator.nextInt(SPRITE_RIGHT_BORDER) + 1;
            int monsterY = generator.nextInt(SPRITE_DOWN_BORDER) + 1;
            Monster monster = new Monster(i+1, monsterX, monsterY);
            badnessBoxSprites.add(monster);
        }
    }

    @Override
    protected WoodyRay createBadSprite() {
        return new WoodyRay();
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
        for (BadnessBoxSprite bad : badnessBoxSprites) {
            if (bad.isVisible()) {
                g.drawImage(bad.getImage(), bad.getX(), bad.getY(), this);
            }
            if(bad.isDestroyed()) {
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

            for (BadnessBoxSprite monster : badnessBoxSprites) {
                int monsterX = monster.getX();
                int monsterY = monster.getY();

                if (!monster.isDestroyed() && woodyRay.isVisible()) {
                    if (
                            shotX >= (monsterX) &&
                            shotX <= (monsterX + SPRITE_WIDTH/2) &&
                            shotY >= (monsterY) &&
                            shotY <= (monsterY + SPRITE_HEIGHT/2)
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
        for (BadnessBoxSprite bad : badnessBoxSprites) {
            if(bad.isDestroyed()) {
                continue ;
            }
            if(time.getTime() - bad.getLastTimeMoved() <= 1000 &&
                    bad.getNextX() > 0 &&
                    bad.getNextX() < SPRITE_RIGHT_BORDER &&
                    bad.getNextY() > 0 &&
                    bad.getNextY() < SPRITE_DOWN_BORDER
                ) {
                bad.act();
                continue ;
            }
            int dx, dy;
            do {
                Random generate = new Random();
                dx = generate.nextInt(3) -1; // [-1, 1];
                dy = generate.nextInt(3) -1; // [-1, 1];
            } while (!(bad.getNextX(dx) > 0 && bad.getNextX(dx) < SPRITE_RIGHT_BORDER && bad.getNextY(dy) > 0 && bad.getNextY(dy) < SPRITE_DOWN_BORDER));

            bad.setLastTimeMoved(time.getTime());
            bad.act(dx, dy);
        }

        // Goop
        Random generator = new Random();

        for (BadnessBoxSprite monster : badnessBoxSprites) {
            int shot = generator.nextInt(15);
            MonsterShot monsterShot = (MonsterShot)monster.getBadnesses().get(0);

            if (shot == CHANCE && monster.isVisible() && monsterShot.isDestroyed()) {
                monsterShot.setDestroyed(false);
                monsterShot.setX(monster.getX());
                monsterShot.setY(monster.getY());
                int dx = monster.getDx() * -1, dy = monster.getDy() * -1;
                if(dx == 0 & dy == 0) dx = 1;
                monsterShot.setDx(dx);
                monsterShot.setDy(dy);
            }

            int shotX = monsterShot.getX();
            int shotY = monsterShot.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !monsterShot.isDestroyed()) {
                if (
                        shotX >= (playerX) &&
                                shotX <= (playerX + SPRITE_WIDTH) &&
                                shotY >= (playerY) &&
                                shotY <= (playerY + SPRITE_HEIGHT)
                ) {
                    players.get(0).setDying(true);
                    monsterShot.setDestroyed(true);
                }
            }

            if (!monsterShot.isDestroyed()) {
                monsterShot.act();
                if (monsterShot.getX() < 0 ||
                    monsterShot.getX() + SHOT_WIDTH > BOARD_WIDTH ||
                    monsterShot.getY() < 0 ||
                    monsterShot.getY() + SHOT_HEIGHT > BOARD_HEIGHT) {
                    monsterShot.setDestroyed(true);
                }
            }
        }
    }

    @Override
    protected void processPlayerShot(Player player, KeyEvent e) {
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
