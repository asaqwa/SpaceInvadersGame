package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import static com.javarush.games.spaceinvaders.ShapeMatrix.PLAYER;

public class PlayerShip extends Ship {
    public PlayerShip() {
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - PLAYER.length - 1);
        setStaticView(PLAYER);
    }
}
