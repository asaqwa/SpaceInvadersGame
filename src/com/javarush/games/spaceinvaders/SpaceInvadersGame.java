package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.PlayerShip;
import com.javarush.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int COMPLEXITY = 5;

    private List<Star> stars;
    private EnemyFleet enemyFleet;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    
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
    }

    private void check() {
        playerShip.verifyHit(enemyBullets);
        removeDeadBullets();
    }

    private void drawScene() {
        drawField();
        playerShip.draw(this);
        enemyBullets.forEach(bul -> bul.draw(this));
        enemyFleet.draw(this);
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.LIGHTGRAY, "");
            }
        }
        for (Star star: stars) star.draw(this);
    }

    private void createGame() {
        createStars();
        enemyFleet = new EnemyFleet();
        enemyBullets = new ArrayList<>();
        playerShip = new PlayerShip();
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
    }
}
