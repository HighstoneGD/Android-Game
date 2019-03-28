package com.mygdx.game.common;

import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.debug.PotType;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private int lives = GameConfig.START_LIVES_AMOUNT;

    private int largeCooldown = GameConfig.LARGE_COOLDOWN;
    private int explosiveCooldown = GameConfig.EXPLOSIVE_COOLDOWN;
    private int ironCooldown = GameConfig.IRON_COOLDOWN;
    private int catCooldown = GameConfig.CAT_COOLDOWN;

    public int getCooldown(PotType type) {
        switch (type) {
            case LARGE:
                return largeCooldown;
            case EXPLOSIVE:
                return explosiveCooldown;
            case IRON:
                return ironCooldown;
            case CAT:
                return catCooldown;
        }
        return 0;
    }

    public void resetLargeCooldown() {
        this.largeCooldown = GameConfig.LARGE_COOLDOWN;
    }

    public void resetExplosiveCooldown() {
        this.explosiveCooldown = GameConfig.EXPLOSIVE_COOLDOWN;
    }

    public void resetIronCooldown() {
        this.ironCooldown = GameConfig.IRON_COOLDOWN;
    }

    public void resetCatCooldown() {
        this.catCooldown = GameConfig.CAT_COOLDOWN;
    }

    public void decrementLargeCooldown() {
        if (largeCooldown > 0) {
            largeCooldown--;
        }
    }

    public void decrementExplosiveCooldown() {
        if (explosiveCooldown > 0) {
            explosiveCooldown--;
        }
    }

    public void decrementIronCooldown() {
        if (ironCooldown > 0) {
            ironCooldown--;
        }
    }

    public void decrementCatCooldown() {
        if (catCooldown > 0) {
            catCooldown--;
        }
    }

    private GameManager() {}

    public int getLives() {
        return lives;
    }

    public void incrementLives() {
        lives++;
    }

    public void takeDamage(int damage) {
        lives -= damage;
    }
}
