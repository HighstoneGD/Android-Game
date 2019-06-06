package com.mygdx.game.util.objects;

import com.mygdx.game.common.enums.BonusType;

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
