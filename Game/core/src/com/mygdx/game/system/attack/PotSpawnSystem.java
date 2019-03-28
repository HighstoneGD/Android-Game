package com.mygdx.game.system.attack;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.debug.PotType;

import java.util.HashMap;
import java.util.Map;

public class PotSpawnSystem extends IntervalSystem {

    private static Map<PotType, Integer> PRIORITIES = new HashMap<PotType, Integer>();

    private static final Logger log = new Logger(PotSpawnSystem.class.getName(), Logger.DEBUG);

    public PotSpawnSystem() {
        super(GameConfig.COOLDOWN_INTERVAL);
        PRIORITIES.put(PotType.SIMPLE, 0);
        PRIORITIES.put(PotType.TRIPLE, 1);
        PRIORITIES.put(PotType.LARGE, 2);
        PRIORITIES.put(PotType.CAT, 3);
        PRIORITIES.put(PotType.EXPLOSIVE, 4);
        PRIORITIES.put(PotType.IRON, 5);
    }

    @Override
    protected void updateInterval() {
        decrementCooldowns();
    }

    public PotType selectPotType() {
        PotType type = PotType.SIMPLE;
        int priority = 0;

        for (PotType type1 : PRIORITIES.keySet()) {
            if (PRIORITIES.get(type1) > priority && GameManager.INSTANCE.getCooldown(type1) == 0) {
                type = type1;
                priority = PRIORITIES.get(type1);
            }
        }

        log.debug("type = " + type);
        return type;
    }

    private void decrementCooldowns() {
        GameManager.INSTANCE.decrementTripleCooldown();
        GameManager.INSTANCE.decrementLargeCooldown();
        GameManager.INSTANCE.decrementExplosiveCooldown();
        GameManager.INSTANCE.decrementIronCooldown();
        GameManager.INSTANCE.decrementCatCooldown();
    }
}
