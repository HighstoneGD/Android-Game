package com.mygdx.game.controlling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.PotType;

public class GameManager {

    public static final boolean DEBUG_TUTORIAL = false;
    public static final boolean DEBUG_ENDLESS_MODE = false;

    public static final GameManager INSTANCE = new GameManager();

    private static final String LEVELS_ACCOMPLISHED_KEY = "levels";
    private static final String TUTORIAL_ACCOMPLISHED_KEY = "tutorial";

    private int levelsAccomplished;

    private Preferences PREFS;
    private float scoreInSeconds;
    private boolean tutorialAccomplished;

    private static int largeCooldown;
    private static int explosiveCooldown;
    private static int ironCooldown;
    private static int bonusCooldown;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(AndroidGame.class.getSimpleName());
        levelsAccomplished = PREFS.getInteger(LEVELS_ACCOMPLISHED_KEY, 0);
        tutorialAccomplished = PREFS.getBoolean(TUTORIAL_ACCOMPLISHED_KEY, false);
    }

    public void updateScore(float amount) {
        scoreInSeconds += amount;
    }

    public float getScore() {
        return scoreInSeconds;
    }

    public void resetScore() {
        scoreInSeconds = 0;
    }

    public void setTutorialAccomplished(boolean tutorialAccomplished) {
        this.tutorialAccomplished = tutorialAccomplished;
        PREFS.putBoolean(TUTORIAL_ACCOMPLISHED_KEY, tutorialAccomplished);
        PREFS.flush();
    }

    public boolean isTutorialAccomplished() {
        if (DEBUG_TUTORIAL) {
            return false;
        }

        return tutorialAccomplished;
    }

    public void levelComplete() {
        levelsAccomplished++;
        if (levelsAccomplished > 8) {
            levelsAccomplished = 8;
        }

        setTutorialAccomplished(false);

        PREFS.putInteger(LEVELS_ACCOMPLISHED_KEY, levelsAccomplished);
        PREFS.flush();
    }

    public int getLevelsAccomplished() {
        return levelsAccomplished;
    }

    public boolean endlessModeUnlocked() {
        if (DEBUG_ENDLESS_MODE) {
            return true;
        }

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
