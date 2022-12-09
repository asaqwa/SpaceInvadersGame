package com.javarush.games.spaceinvaders.gameobjects;

import static com.javarush.games.spaceinvaders.ShapeMatrix.BOSS_ANIMATION_FIRST;
import static com.javarush.games.spaceinvaders.ShapeMatrix.BOSS_ANIMATION_SECOND;

public class Boss extends EnemyShip {
    private int frameCount = 0;
    public Boss(double x, double y) {
        super(x, y);
        setAnimatedView(BOSS_ANIMATION_FIRST, BOSS_ANIMATION_SECOND);
    }

    @Override
    public void nextFrame() {
        frameCount++;
        if (frameCount%10 == 0 || !isAlive) super.nextFrame();
    }
}
