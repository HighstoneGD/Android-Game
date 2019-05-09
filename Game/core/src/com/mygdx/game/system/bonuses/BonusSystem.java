package com.mygdx.game.system.bonuses;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.attack.TargetSystem;

import java.util.HashMap;
import java.util.Map;

public class BonusSystem extends IntervalSystem {

    private static final Logger log = new Logger(BonusSystem.class.getName(), Logger.DEBUG);
    private BasicGameScreen screen;
    private Map<BonusType, Integer> priorities;

    public BonusSystem(float bonusSpawnSpeed, BasicGameScreen screen) {
        super(bonusSpawnSpeed);
        this.screen = screen;
        priorities = new HashMap<BonusType, Integer>();
        priorities.put(BonusType.GOLD, 0);
        priorities.put(BonusType.SPEED, 1);
        priorities.put(BonusType.ARMOR, 2);
        priorities.put(BonusType.TIME_DECELERATION, 3);
        priorities.put(BonusType.LIFE, 4);
    }

    @Override
    protected void updateInterval() {
        BonusType type = selectBonusType();
        int x = getEngine().getSystem(TargetSystem.class).selectTargetX();
        int y = getEngine().getSystem(TargetSystem.class).selectTargetY();

        BonusPotSystem bonusPotSystem = new BonusPotSystem(x, y, type);
        getEngine().addSystem(bonusPotSystem);
        Thread thread = new Thread(bonusPotSystem);
        thread.start();
    }

    private BonusType selectBonusType() {
        BonusType type = BonusType.GOLD;

        for (BonusType type1 : priorities.keySet()) {
            if (priorities.get(type) < priorities.get(type1) && GameManager.COOLDOWNS_MANAGER.getBonusCooldown(type1) == 0) {
                type = type1;
            }
        }

        log.debug("type = " + type);
        GameManager.COOLDOWNS_MANAGER.decrementBonusCooldowns();
        GameManager.COOLDOWNS_MANAGER.resetBonusCooldown(type);
        return type;
    }
}
