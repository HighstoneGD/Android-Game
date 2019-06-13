package com.mygdx.game.system.attack.bonus;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameData;
import com.mygdx.game.controlling.BonusManager;

public class SpeedUpBonusSystem extends BonusSystem {

    private static final Logger log = new Logger(SpeedUpBonusSystem.class.getName(), Logger.DEBUG);

    private boolean inAction;
    private float timer;

    @Override
    public void picked() {
        log.debug("speed up");
        BonusManager.INSTANCE.speedUp();
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
        timer = GameData.SPEED_UP_TIME;
    }

    private void checkTimer() {
        if (timer <= 0) {
            BonusManager.INSTANCE.resetSpeed();
        }
    }
}
