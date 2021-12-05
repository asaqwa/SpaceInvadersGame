package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.*;

public class Star extends GameObject {
    public Star(double x, double y) {
        super(x, y);
    }
    private static final String STAR_SIGN = "\u2606";

    public void draw(Game game) {
        game.setCellValueEx((int)Math.round(x), (int)Math.round(y),Color.NONE, STAR_SIGN, Color.SNOW, 100);
    }
}
