package com.mygdx.game.controlling;

import com.mygdx.game.common.Constants;

public class HealthManager {

    private static int lives = Constants.START_LIVES_AMOUNT;
    private static int armor = 0;

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
