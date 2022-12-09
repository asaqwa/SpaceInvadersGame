package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;

import static com.javarush.games.spaceinvaders.ShapeMatrix.*;

public class EnemyShip extends Ship {
    public int score = 15;
    public EnemyShip(double x, double y) {
        super(x, y);
        setStaticView(ENEMY);
    }

    public void move(Direction direction, double speed) {
        switch (direction) {
            case RIGHT:
                x += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case DOWN:
                y += 2;
                break;
        }
    }

    @Override
    public Bullet fire() {
        return new Bullet(x + 1, y + height, Direction.DOWN);
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        isAlive = false;
        setAnimatedView(false, KILL_ENEMY_ANIMATION_FIRST, KILL_ENEMY_ANIMATION_SECOND, KILL_ENEMY_ANIMATION_THIRD);
    }
}
