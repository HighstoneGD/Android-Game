package com.mygdx.game.controlling;

import com.mygdx.game.common.Constants;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.PotType;

public class CooldownsManager {

    private static int largeCooldown = Constants.LARGE_COOLDOWN;
    private static int explosiveCooldown = Constants.EXPLOSIVE_COOLDOWN;
    private static int ironCooldown = Constants.IRON_COOLDOWN;
    private static int catCooldown = Constants.CAT_COOLDOWN;

    private static int speedCooldown = Constants.SPEED_COOLDOWN;
    private static int armorCooldown = Constants.ARMOR_COOLDOWN;
    private static int timeDecelerationCooldown = Constants.TIME_DECELERATION_COOLDOWN;
    private static int lifeCooldown = Constants.LIFE_COOLDOWN;

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
        if (type == PotType.LARGE) {
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
        largeCooldown = Constants.LARGE_COOLDOWN;
    }

    private void resetExplosiveCooldown() {
        explosiveCooldown = Constants.EXPLOSIVE_COOLDOWN;
    }

    private void resetIronCooldown() {
        ironCooldown = Constants.IRON_COOLDOWN;
    }

    private void resetCatCooldown() {
        catCooldown = Constants.CAT_COOLDOWN;
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
        if (type == BonusType.SPEED) {
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

}
