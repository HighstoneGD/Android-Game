package com.mygdx.game.common;

import com.mygdx.game.debug.GameConfig;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private int lives = GameConfig.START_LIVES_AMOUNT;

    private GameManager() {

    }

    public int getLives() {
        return lives;
    }

    public void incrementLives() {
        lives++;
    }

    public void getDamage(int damage) {
        lives -= damage;
    }
}
