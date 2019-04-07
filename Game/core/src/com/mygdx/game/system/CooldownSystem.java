package com.mygdx.game.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.debug.GameConfig;

public class CooldownSystem extends IntervalSystem {

    public CooldownSystem() {
        super(GameConfig.COOLDOWN_INTERVAL);
    }

    @Override
    protected void updateInterval() {
        decrementCooldowns();
    }

    private void decrementCooldowns() {
        GameManager.INSTANCE.decrementLargeCooldown();
        GameManager.INSTANCE.decrementIronCooldown();
        GameManager.INSTANCE.decrementCatCooldown();
        GameManager.INSTANCE.decrementExplosiveCooldown();
    }
}
