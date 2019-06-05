package com.mygdx.game.controlling;

import com.mygdx.game.common.Constants;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.PotType;

public class CooldownsManager {

    private static int largeCooldown;
    private static int explosiveCooldown;
    private static int ironCooldown;
    private static int bonusCooldown;

    private static int speedCooldown;
    private static int armorCooldown;
    private static int timeDecelerationCooldown;
    private static int lifeCooldown;

    public static int getPotCooldown(PotType type) {
        switch (type) {
            case LARGE:
                return largeCooldown;
            case EXPLOSIVE:
                return explosiveCooldown;
            case IRON:
                return ironCooldown;
            case BONUS:
                return bonusCooldown;
        }
        return 0;
    }

    public static int getBonusCooldown(BonusType type) {
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

    public static void resetCooldown(PotType type) {
        if (type == PotType.LARGE) {
            largeCooldown = Constants.LARGE_COOLDOWN;
        } else if (type == PotType.IRON) {
            ironCooldown = Constants.IRON_COOLDOWN;
        } else if (type == PotType.EXPLOSIVE) {
            explosiveCooldown = Constants.EXPLOSIVE_COOLDOWN;
        } else if (type == PotType.BONUS) {
            bonusCooldown = Constants.BONUS_COOLDOWN;
        }
    }

    public static void resetBonusCooldown(BonusType type) {
        if (type == BonusType.SPEED) {
            speedCooldown = Constants.SPEED_COOLDOWN;
        } else if (type == BonusType.ARMOR) {
            armorCooldown = Constants.ARMOR_COOLDOWN;
        } else if (type == BonusType.TIME_DECELERATION) {
            timeDecelerationCooldown = Constants.TIME_DECELERATION_COOLDOWN;
        } else if (type == BonusType.LIFE) {
            lifeCooldown = Constants.LIFE_COOLDOWN;
        }
    }

    public static void resetCooldown() {
        largeCooldown = Constants.LARGE_COOLDOWN;
        ironCooldown = Constants.IRON_COOLDOWN;
        explosiveCooldown = Constants.EXPLOSIVE_COOLDOWN;
        bonusCooldown = Constants.BONUS_COOLDOWN;
        speedCooldown = Constants.SPEED_COOLDOWN;
        armorCooldown = Constants.ARMOR_COOLDOWN;
        timeDecelerationCooldown = Constants.TIME_DECELERATION_COOLDOWN;
        lifeCooldown = Constants.LIFE_COOLDOWN;
    }

    public static void decrementCooldowns() {

        if (largeCooldown > 0) {
            largeCooldown--;
        }

        if (explosiveCooldown > 0) {
            explosiveCooldown--;
        }

        if (ironCooldown > 0) {
            ironCooldown--;
        }

        if (bonusCooldown > 0) {
            bonusCooldown--;
        }

    }

    public static void decrementBonusCooldowns() {
        if (speedCooldown > 0) {
            speedCooldown--;
        }

        if (armorCooldown > 0) {
            armorCooldown--;
        }

        if (timeDecelerationCooldown > 0) {
            timeDecelerationCooldown--;
        }

        if (lifeCooldown > 0) {
            lifeCooldown--;
        }
    }

}
