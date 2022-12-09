package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {
    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ShapeMatrix.ENEMY.length + 1;

    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;

    public EnemyFleet() {
        createShips();
    }

    public void draw(Game game) {
        for (EnemyShip ship : ships) {
            ship.draw(game);
        }
    }

    public void move() {
        if (ships.isEmpty()) return;

        Direction currentDirection = direction;

        if (direction == Direction.LEFT && getLeftBorder() < 0) {
            direction = Direction.RIGHT;
            currentDirection = Direction.DOWN;
        } else if (direction == Direction.RIGHT && getRightBorder() > SpaceInvadersGame.WIDTH) {
            direction = Direction.LEFT;
            currentDirection = Direction.DOWN;
        }

        double speed = getSpeed();
        Direction finalCurrentDirection = currentDirection;
        ships.forEach(s -> s.move(finalCurrentDirection, speed));
    }

    public Bullet fire (Game game) {
        if (ships.isEmpty()) return null;

        int x = game.getRandomNumber(100 / SpaceInvadersGame.COMPLEXITY);
        if (x > 0) return null;

        x = game.getRandomNumber(ships.size());
        return ships.get(x).fire();
    }

    public void verifyHit(List<Bullet> bullets) {
        for (Ship ship : ships) {
            bullets.forEach((bullet) -> {
                if (ship.isCollision(bullet) && ship.isAlive && bullet.isAlive) {
                    ship.kill();
                    bullet.kill();
                }
            });
        }
    }

    private double getLeftBorder() {
        return ships.stream().mapToDouble(ship -> ship.x).min().getAsDouble();
    }

    private double getRightBorder() {
        return ships.stream().mapToDouble(ship -> ship.x).max().getAsDouble() + ships.get(0).width;
    }

    private double getSpeed() {
        return Double.min(2d, (3d/ships.size()));
    }

    private void createShips() {
        ships = new ArrayList<>();
        for (int x = 0; x < COLUMNS_COUNT; x++) {
            for (int y = 0; y < ROWS_COUNT; y++) {
                ships.add(new EnemyShip(x*STEP, y*STEP+12));
            }
        }
    }

    public void deleteHiddenShips() {
        ships.removeIf((ship) -> !ship.isVisible());
    }
}
