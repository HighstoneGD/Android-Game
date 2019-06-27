package com.mygdx.game.controlling;

import com.mygdx.game.ScoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private static final String USER_TEST_ID = "6ihTtEHgQjYlInYbqrqK8ds2cHX2";

    private List<ScoreListener> listeners;
    public UserData userData;

    public ScoreManager() {
        listeners = new ArrayList<>();
        userData = new UserData(USER_TEST_ID, new ArrayList<>());
    }

    public void addListener(ScoreListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ScoreListener listener : listeners) {
            listener.onUpdate();
        }
    }

    public void addScore(int score) {
        if (userData.highscores.size() < 10) {
            userData.highscores.add(score);
        } else if (userData.highscores.get(0) < score) {
            userData.highscores.set(0, score);
        }

        Collections.sort(userData.highscores);

        notifyListeners();
    }

    public String getUserId() {
        return USER_TEST_ID;
    }
}
