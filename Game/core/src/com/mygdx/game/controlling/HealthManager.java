package com.mygdx.game.controlling;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.util.interfaces.SpeedStateListener;

import java.util.ArrayList;
import java.util.List;

public class HealthManager {

    private static final Logger log = new Logger(HealthManager.class.getName(), Logger.DEBUG);

    private static List<SpeedStateListener> listeners = new ArrayList<>();

    private static int lives;
    private static boolean armor;

    public static void subscribe(SpeedStateListener listener) {
        listeners.add(listener);
    }

    private static void notifyListeners(BonusType type) {
        for (SpeedStateListener listener : listeners) {
            if (type == BonusType.ARMOR) {
                listener.onArmorPicked();
            } else if (type == BonusType.LIFE) {
                listener.onHPPicked();
            }
        }
    }

    public static int getLives() {
        return lives;
    }

    public static boolean hasArmor() {
        return armor;
    }

    public static void pickArmor() {
        armor = true;
        notifyListeners(BonusType.ARMOR);
    }

    public static void removeArmor() {
        armor = false;
    }

    public static void incrementLives() {
        if (lives < 3) {
            lives++;
            notifyListeners(BonusType.LIFE);
        }
    }

    public static void takeDamage(int damage) {
        log.debug("damage taken: " + damage);
        int dam = damage;

        if (armor) {
            dam--;
        }

        lives -= dam;

        if (lives < 0) {
            lives = 0;
        }
    }

    public static void reset() {
        lives = GameData.START_LIVES_AMOUNT;
        armor = false;
    }

    public static void reset(int livesAmount) {
        lives = livesAmount;
        armor = false;
    }

}
