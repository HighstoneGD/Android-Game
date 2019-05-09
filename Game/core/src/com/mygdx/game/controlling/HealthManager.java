package com.mygdx.game.controlling;

import com.mygdx.game.common.Constants;

public class HealthManager {

    private static int lives;
    private static int armor;

    public static int getLives() {
        return lives;
    }

    public static int getArmor() {
        return armor;
    }

    public static void pickArmor() {
        armor = 1;
    }

    public static void incrementLives() {
        lives++;
    }

    public static void takeDamage(int damage) {
        int dam = damage;

        if (armor > 0) {
            armor = 0;
            dam--;
        }

        lives -= dam;
    }

    public static void reset() {
        lives = Constants.START_LIVES_AMOUNT;
        armor = 0;
    }

}
