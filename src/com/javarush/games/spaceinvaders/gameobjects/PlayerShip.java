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

    public void setDirection(Direction newDirection) {
        if (newDirection != Direction.DOWN) this.direction = newDirection;
    }
}
