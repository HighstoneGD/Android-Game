package com.mygdx.game.common.objects;

public class Bonus {

    public float time;
    public BonusType type;

    public Bonus(float time, BonusType type) {
        this.time = time;
        this.type = type;
    }

    public void reduceTimer(float deltaTime) {
        if (time > 0) {
            time -= deltaTime;
        }
    }
}
