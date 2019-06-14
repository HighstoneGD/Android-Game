package com.mygdx.game.system.attack.bonus;

import com.mygdx.game.common.GameData;
import com.mygdx.game.controlling.HealthManager;

public class ArmorBonusSystem extends BonusSystem {

    private boolean inAction;
    private float timer;

    public void picked() {
        HealthManager.pickArmor();
        startTimer();
    }

    @Override
    public void update(float deltaTime) {
        if (inAction) {
            timer -= deltaTime;
            checkTimer();
        }
    }

    private void startTimer() {
        inAction = true;
        timer = GameData.ARMOR_TIME;
    }

    private void checkTimer() {
        if (timer <= 0) {
            HealthManager.removeArmor();
        }
    }
}
