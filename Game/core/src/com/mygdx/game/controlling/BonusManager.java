package com.mygdx.game.controlling;

public class BonusManager {

    public static final BonusManager INSTANCE = new BonusManager();

    private float timeModifier;

    private BonusManager() {
        resetSpeed();
    }

    public void speedUp() {
        timeModifier = 0.5f;
    }

    public void resetSpeed() {
        timeModifier = 1f;
    }

    public float getTimeModifier() {
        return timeModifier;
    }
}
