package com.mygdx.game.controlling;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;

public class HealthManager {

    private static final Logger log = new Logger(HealthManager.class.getName(), Logger.DEBUG);

    private static int lives;
    private static boolean armor;

    public static int getLives() {
        return lives;
    }

    public static boolean hasArmor() {
        return armor;
    }

    public static void pickArmor() {
        armor = true;
    }

    public static void incrementLives() {
        if (lives < 3) {
            lives++;
        }
    }

    public static void takeDamage(int damage) {
        log.debug("damage taken: " + damage);
        int dam = damage;

        if (armor) {
            armor = false;
            dam--;
        }

        lives -= dam;
    }

    public static void reset() {
        lives = Constants.START_LIVES_AMOUNT;
        armor = false;
    }

}
