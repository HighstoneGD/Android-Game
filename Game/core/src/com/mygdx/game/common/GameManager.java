package com.mygdx.game.common;

import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.debug.PotType;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private int lives = GameConfig.START_LIVES_AMOUNT;
    private int armor = 0;

    private static int lefts = 0;
    private static int rights = 0;
    private static int ups = 0;
    private static int downs = 0;

    private int largeCooldown = GameConfig.LARGE_COOLDOWN;
    private int explosiveCooldown = GameConfig.EXPLOSIVE_COOLDOWN;
    private int ironCooldown = GameConfig.IRON_COOLDOWN;
    private int catCooldown = GameConfig.CAT_COOLDOWN;

    public void incrementLefts() {
        lefts++;
    }

    public void incrementRights() {
        rights++;
    }

    public void incrementUps() {
        ups++;
    }

    public void incrementDowns() {
        downs++;
    }

    public int leftOrRight() {
        // 0 if left, 1 if right
        if (lefts > rights) {
            return 0;
        } else {
            return 1;
        }
    }

    public int upOrDown() {
        // 0 if up, 1 if down
        if (ups > downs) {
            return 0;
        } else {
            return 1;
        }
    }

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
        if (type == PotType.SIMPLE) {
            return;
        } else if (type == PotType.LARGE) {
            resetLargeCooldown();
        } else if (type == PotType.IRON) {
            resetIronCooldown();
        } else if (type == PotType.EXPLOSIVE) {
            resetExplosiveCooldown();
        } else if (type == PotType.CAT) {
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

    public int getArmor() {
        return armor;
    }

    public void pickArmor() {
        armor = 1;
    }

    public void incrementLives() {
        lives++;
    }

    public void takeDamage(int damage) {
        int dam = damage;

        if (armor > 0) {
            armor = 0;
            dam--;
        }

        lives -= dam;
    }
}
