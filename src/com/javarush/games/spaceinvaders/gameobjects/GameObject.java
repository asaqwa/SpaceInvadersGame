package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.*;

public class GameObject {
    public double x;
    public double y;
    public int[][] matrix;
    public int width;
    public int height;

    public void draw(Game game) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                game.setCellValueEx((int)Math.round(x)+j, (int)Math.round(y)+i, Color.values()[matrix[i][j]], "");
            }
        }
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }
}
