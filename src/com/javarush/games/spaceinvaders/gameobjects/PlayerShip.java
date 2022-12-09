package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.List;

import static com.javarush.games.spaceinvaders.ShapeMatrix.*;

public class PlayerShip extends Ship {
    private Direction direction = Direction.UP;
    public PlayerShip() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - PLAYER.length - 1);
        setStaticView(PLAYER);
    }

    public void verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) return;
        if (isAlive) {
            for (Bullet bullet : bullets) {
                if (bullet.isAlive && isCollision(bullet)) {
                    kill();
                    bullet.kill();
                }
            }
        }
    }

    @Override
    public void kill() {
        if (!isAlive) return;
        isAlive = false;
        setAnimatedView(KILL_PLAYER_ANIMATION_FIRST, KILL_PLAYER_ANIMATION_SECOND, KILL_PLAYER_ANIMATION_THIRD, DEAD_PLAYER);
    }

    @Override
    public Bullet fire() {
        if (!isAlive) return null;
        return new Bullet(x+2, y-BULLET.length, Direction.UP);
    }

    public void setDirection(Direction newDirection) {
        if (newDirection != Direction.DOWN) this.direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    public void move() {
        if (!isAlive) return;

        if (direction == Direction.LEFT) x--;
        else if (direction == Direction.RIGHT) x++;

        if (x<0) x = 0;
        else if ((x+width)>SpaceInvadersGame.WIDTH) x = SpaceInvadersGame.WIDTH - width;
    }
}
