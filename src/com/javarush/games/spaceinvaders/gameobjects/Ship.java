package com.javarush.games.spaceinvaders.gameobjects;


import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject {
    public boolean isAlive = true;

    private int frameIndex;
    private List<int[][]> frames;

    public Ship(double x, double y) {
        super(x, y);
    }

    public void setStaticView(int[][] viewFrame) {
        setMatrix(viewFrame);
        frames = new ArrayList<>();
        frames.add(viewFrame);
        frameIndex = 0;
    }

    public void setAnimatedView(int[][]... viewFrames) {
        setMatrix(viewFrames[0]);
        frames = Arrays.asList(viewFrames);
        frameIndex = 0;
    }

    public boolean isVisible() {
        if (!isAlive && frameIndex >= frames.size()) return false;
        return true;
    }

    public void nextFrame() {
        frameIndex++;
        if (frameIndex >= frames.size()) return;
        matrix = frames.get(frameIndex);
    }

    public Bullet fire() {
        return null;
    }

    public void kill() {
        isAlive = false;
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        nextFrame();
    }
}
