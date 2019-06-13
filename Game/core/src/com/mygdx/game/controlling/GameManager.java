package com.mygdx.game.controlling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.PotType;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGHSCORE_KEY = "highscore";
    private static final String LEVELS_ACCOMPLISHED_KEY = "levels";

    private int levelsAccomplished;

    private Preferences PREFS;
    private float scoreInSeconds;
    private float highscoreInSeconds;

    private static int largeCooldown;
    private static int explosiveCooldown;
    private static int ironCooldown;
    private static int bonusCooldown;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(AndroidGame.class.getSimpleName());
        highscoreInSeconds = PREFS.getFloat(HIGHSCORE_KEY, 0);
        levelsAccomplished = PREFS.getInteger(LEVELS_ACCOMPLISHED_KEY, 0);
    }

    public void updateHighscore() {
        if (scoreInSeconds < highscoreInSeconds) {
            return;
        }

        highscoreInSeconds = scoreInSeconds;
        PREFS.putFloat(HIGHSCORE_KEY, highscoreInSeconds);
        PREFS.flush();
    }

    public void updateScore(float amount) {
        scoreInSeconds += amount;
    }

    public float getScore() {
        return scoreInSeconds;
    }

    public float getHighscore() {
        return highscoreInSeconds;
    }

    public void resetScore() {
        scoreInSeconds = 0;
    }

    public void levelComplete() {
        levelsAccomplished++;
        if (levelsAccomplished > 8) {
            levelsAccomplished = 8;
        }

        PREFS.putInteger(LEVELS_ACCOMPLISHED_KEY, levelsAccomplished);
        PREFS.flush();
    }

    public int getLevelsAccomplished() {
        return levelsAccomplished;
    }

    public boolean endlessModeUnlocked() {
        return levelsAccomplished == 8;
    }

    public int getPotCooldown(PotType type) {
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

    public void resetCooldown(PotType type) {
        if (type == PotType.LARGE) {
            largeCooldown = GameData.LARGE_COOLDOWN;
        } else if (type == PotType.IRON) {
            ironCooldown = GameData.IRON_COOLDOWN;
        } else if (type == PotType.EXPLOSIVE) {
            explosiveCooldown = GameData.EXPLOSIVE_COOLDOWN;
        } else if (type == PotType.BONUS) {
            bonusCooldown = GameData.BONUS_COOLDOWN;
        }
    }

    public void resetCooldown() {
        largeCooldown = GameData.LARGE_COOLDOWN;
        ironCooldown = GameData.IRON_COOLDOWN;
        explosiveCooldown = GameData.EXPLOSIVE_COOLDOWN;
        bonusCooldown = GameData.BONUS_COOLDOWN;
    }

    public void decrementCooldowns() {

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
}
