package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.PlayerShip;
import com.javarush.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int COMPLEXITY = 5;
    private static final int PLAYER_BULLETS_MAX = 1;

    private List<Star> stars;
    private EnemyFleet enemyFleet;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    private List<Bullet> playerBullets ;
    private boolean isGameStopped;
    private int animationsCount;

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet bullet = enemyFleet.fire(this);
        if (bullet != null) enemyBullets.add(bullet);
        drawScene();
    }

    private void moveSpaceObjects() {
        enemyFleet.move();
        enemyBullets.forEach(Bullet::move);
        playerShip.move();
        playerBullets.forEach(Bullet::move);
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE) {
            if (isGameStopped) {
                createGame();
                return;
            }

            if (playerBullets.size() < PLAYER_BULLETS_MAX) {
                Bullet bullet = playerShip.fire();
                if (bullet != null) playerBullets.add(bullet);
            }

        } else if (key == Key.LEFT) {
            playerShip.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            playerShip.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.LEFT && playerShip.getDirection() == Direction.LEFT
                || key == Key.RIGHT && playerShip.getDirection() == Direction.RIGHT)
            playerShip.setDirection(Direction.UP);
    }

    private void check() {
        playerShip.verifyHit(enemyBullets);
        enemyFleet.verifyHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        removeDeadBullets();
        if (!playerShip.isAlive) stopGameWithDelay();
    }

    private void drawScene() {
        drawField();
        playerShip.draw(this);
        enemyBullets.forEach(bul -> bul.draw(this));
        enemyFleet.draw(this);
        playerBullets.forEach(bul -> bul.draw(this));
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.LIGHTGRAY, "");
            }
        }
        for (Star star: stars) star.draw(this);
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x<0 || y<0 || x >= WIDTH || y >= HEIGHT) return;
        super.setCellValueEx(x, y, cellColor, value);
    }

    private void createGame() {
        createStars();
        enemyFleet = new EnemyFleet();
        enemyBullets = new ArrayList<>();
        playerShip = new PlayerShip();
        playerBullets = new ArrayList<>();
        isGameStopped = false;
        animationsCount = 0;
        drawScene();
        setTurnTimer(40);
    }

    private void createStars() {
        stars = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            stars.add(new Star((int) (Math.random()*WIDTH), (int) (Math.random()*HEIGHT)));
        }
    }

    private void removeDeadBullets() {
        enemyBullets.removeIf(bullet -> !bullet.isAlive || bullet.y >= HEIGHT-1);
        playerBullets.removeIf(bullet -> !bullet.isAlive || bullet.y + bullet.height < 0);
    }

    private void stopGame(boolean isWin) {
        isGameStopped = true;
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.WHITE, "YOU WIN", Color.GREEN, 32);
        } else {
            showMessageDialog(Color.WHITE, "YOU LOSE", Color.RED, 32);
        }
    }

    private void stopGameWithDelay() {
        animationsCount++;
        if (animationsCount>=10) stopGame(playerShip.isAlive);
    }
}
