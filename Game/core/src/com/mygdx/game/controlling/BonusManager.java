package com.mygdx.game.controlling;

import com.mygdx.game.util.interfaces.SpeedStateListener;

import java.util.ArrayList;
import java.util.List;

public class BonusManager {

    public static final BonusManager INSTANCE = new BonusManager();
    private static List<SpeedStateListener> listeners = new ArrayList<>();

    private float timeModifier;

    public void subscribe(SpeedStateListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (SpeedStateListener listener : listeners) {
            listener.onSpeedUp();
        }
    }

    private BonusManager() {
        resetSpeed();
    }

    public void speedUp() {
        timeModifier = 0.5f;
        notifyListeners();
    }

    public void resetSpeed() {
        timeModifier = 1f;
    }

    public float getTimeModifier() {
        return timeModifier;
    }
}
