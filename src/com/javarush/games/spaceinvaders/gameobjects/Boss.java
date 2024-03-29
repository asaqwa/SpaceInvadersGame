package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;

import static com.javarush.games.spaceinvaders.ShapeMatrix.*;

public class Boss extends EnemyShip {
    private int frameCount = 0;
    public Boss(double x, double y) {
        super(x, y);
        setAnimatedView(true, BOSS_ANIMATION_FIRST, BOSS_ANIMATION_SECOND);
        score = 100;
    }

    @Override
    public void nextFrame() {
        frameCount++;
        if (frameCount%10 == 0 || !isAlive) super.nextFrame();
    }

    @Override
    public Bullet fire() {
        if (!isAlive) return null;
        if (matrix == BOSS_ANIMATION_FIRST)
            return new Bullet(x + 6, y + height, Direction.DOWN);
        else
            return new Bullet(x, y + height, Direction.DOWN);
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        isAlive = false;
        setAnimatedView(false, KILL_BOSS_ANIMATION_FIRST, KILL_BOSS_ANIMATION_SECOND, KILL_BOSS_ANIMATION_THIRD);
    }
}
