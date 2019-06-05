package com.mygdx.game.controlling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.AndroidGame;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGHSCORE_KEY = "highscore";

    private Preferences PREFS;
    private float scoreInSeconds;
    private float highscoreInSeconds;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(AndroidGame.class.getSimpleName());
        highscoreInSeconds = PREFS.getFloat(HIGHSCORE_KEY, 0);
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

}
