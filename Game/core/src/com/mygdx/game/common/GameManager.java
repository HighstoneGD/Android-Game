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

    public void resetCooldown(PotType type) {
        switch (type) {
            case SIMPLE:
                return;
            case LARGE:
                resetLargeCooldown();
            case EXPLOSIVE:
                resetExplosiveCooldown();
            case IRON:
                resetIronCooldown();
            case CAT:
                resetCatCooldown();
        }
    }

    private void resetLargeCooldown() {
        this.largeCooldown = GameConfig.LARGE_COOLDOWN;
    }

    private void resetExplosiveCooldown() {
        this.explosiveCooldown = GameConfig.EXPLOSIVE_COOLDOWN;
    }

    private void resetIronCooldown() {
        this.ironCooldown = GameConfig.IRON_COOLDOWN;
    }

    private void resetCatCooldown() {
        this.catCooldown = GameConfig.CAT_COOLDOWN;
    }

    public void decrementCooldowns() {
        decrementLargeCooldown();
        decrementExplosiveCooldown();
        decrementIronCooldown();
        decrementCatCooldown();
    }

    private void decrementLargeCooldown() {
        if (largeCooldown > 0) {
            largeCooldown--;
        }
    }

    private void decrementExplosiveCooldown() {
        if (explosiveCooldown > 0) {
            explosiveCooldown--;
        }
    }

    private void decrementIronCooldown() {
        if (ironCooldown > 0) {
            ironCooldown--;
        }
    }

    private void decrementCatCooldown() {
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
