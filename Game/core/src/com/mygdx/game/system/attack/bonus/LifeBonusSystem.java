package com.mygdx.game.system.attack.bonus;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.controlling.HealthManager;

public class LifeBonusSystem extends BonusSystem {
    private static final Logger log = new Logger(LifeBonusSystem.class.getName(), Logger.DEBUG);

    @Override
    public void picked() {
        HealthManager.incrementLives();
    }
}
