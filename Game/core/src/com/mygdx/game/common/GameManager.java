package com.mygdx.game.common;

import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.PotType;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static int lives = Constants.START_LIVES_AMOUNT;
    private static int armor = 0;

    private static int lefts = 0;
    private static int rights = 0;
    private static int ups = 0;
    private static int downs = 0;
    private static int avoided = 0;
    private static int avoidedRecord = 0;

    private static int largeCooldown = Constants.LARGE_COOLDOWN;
    private static int explosiveCooldown = Constants.EXPLOSIVE_COOLDOWN;
    private static int ironCooldown = Constants.IRON_COOLDOWN;
    private static int catCooldown = Constants.CAT_COOLDOWN;

    private static int speedCooldown = Constants.SPEED_COOLDOWN;
    private static int armorCooldown = Constants.ARMOR_COOLDOWN;
    private static int timeDecelerationCooldown = Constants.TIME_DECELERATION_COOLDOWN;
    private static int lifeCooldown = Constants.LIFE_COOLDOWN;

    public void avoided() {
        avoided++;
    }

    public void resetAvoided() {
        if (avoided > avoidedRecord) {
            avoidedRecord = avoided;
        }

        avoided = 0;
    }

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
        this.largeCooldown = Constants.LARGE_COOLDOWN;
    }

    private void resetExplosiveCooldown() {
        this.explosiveCooldown = Constants.EXPLOSIVE_COOLDOWN;
    }

    private void resetIronCooldown() {
        this.ironCooldown = Constants.IRON_COOLDOWN;
    }

    private void resetCatCooldown() {
        this.catCooldown = Constants.CAT_COOLDOWN;
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

    public void resetBonusCooldown(BonusType type) {
        if (type == BonusType.GOLD) {
            return;
        } else if (type == BonusType.SPEED) {
            resetSpeedCooldown();
        } else if (type == BonusType.ARMOR) {
            resetArmorCooldown();
        } else if (type == BonusType.TIME_DECELERATION) {
            resetTimeDecelerationCooldown();
        } else if (type == BonusType.LIFE) {
            resetLifeCooldown();
        }
    }

    public int getBonusCooldown(BonusType type) {
        switch (type) {
            case SPEED:
                return speedCooldown;
            case ARMOR:
                return armorCooldown;
            case TIME_DECELERATION:
                return timeDecelerationCooldown;
            case LIFE:
                return lifeCooldown;
        }
        return 0;
    }

    private void resetSpeedCooldown() {
        speedCooldown = Constants.SPEED_COOLDOWN;
    }

    private void resetArmorCooldown() {
        armorCooldown = Constants.ARMOR_COOLDOWN;
    }

    private void resetTimeDecelerationCooldown() {
        timeDecelerationCooldown = Constants.TIME_DECELERATION_COOLDOWN;
    }

    private void resetLifeCooldown() {
        lifeCooldown = Constants.LIFE_COOLDOWN;
    }

    public void decrementBonusCooldowns() {
        decrementSpeedCooldown();
        decrementArmorCooldown();
        decrementTimeDecelerationCooldown();
        decrementLifeCooldown();
    }

    private void decrementSpeedCooldown() {
        if (speedCooldown > 0) {
            speedCooldown--;
        }
    }

    private void decrementArmorCooldown() {
        if (armorCooldown > 0) {
            armorCooldown--;
        }
    }

    private void decrementTimeDecelerationCooldown() {
        if (timeDecelerationCooldown > 0) {
            timeDecelerationCooldown--;
        }
    }

    private void decrementLifeCooldown() {
        if (lifeCooldown > 0) {
            lifeCooldown--;
        }
    }

    private GameManager() {
    }

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