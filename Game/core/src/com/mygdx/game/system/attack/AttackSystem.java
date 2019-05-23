package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.controlling.CooldownsManager;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.attack.potsystems.SystemCreator;

import java.util.HashMap;
import java.util.Map;

public class AttackSystem extends IntervalSystem {

    private static final Logger log = new Logger(AttackSystem.class.getName(), Logger.DEBUG);
    private BasicGameScreen screen;
    private Map<PotType, Integer> potsPriorities;
    private Map<BonusType, Integer> bonusPriorities;
    private PooledEngine engine;

    public AttackSystem(float attackSpeed, BasicGameScreen screen, PooledEngine engine) {
        super(attackSpeed);
        this.screen = screen;

        initPotPriorities();
        initBonusPriorities();

        this.engine = engine;
    }

    @Override
    protected void updateInterval() {
        PotType type = selectPotType();
        int x = getEngine().getSystem(TargetSystem.class).selectTargetX();
        int y = getEngine().getSystem(TargetSystem.class).selectTargetY();

        if (type == PotType.SIMPLE) {
            SystemCreator.createSimplePotSystem(engine, screen, x, y);
        } else if (type == PotType.LARGE) {
            SystemCreator.createLargePotSystem(engine, screen, x, y);
        } else if (type == PotType.EXPLOSIVE) {
            SystemCreator.createExplosivePotSystem(engine, x, y);
        } else if (type == PotType.IRON) {
            SystemCreator.createIronPotSystem(engine, screen, x, y);
        } else if (type == PotType.CAT) {
            SystemCreator.createCatSystem(engine, screen, x);
        } else if (type == PotType.BONUS) {
            SystemCreator.createBonusPotSystem(engine, screen, selectBonusType(), x, y);
        }
    }

    private PotType selectPotType() {
        PotType type = PotType.SIMPLE;

        for (PotType type1 : potsPriorities.keySet()) {
            if (potsPriorities.get(type) < potsPriorities.get(type1) && CooldownsManager.getPotCooldown(type1) == 0) {
                type = type1;
            }
        }

//        log.debug("type = " + type);
        CooldownsManager.decrementCooldowns();
        CooldownsManager.resetCooldown(type);
        return type;
    }

    private BonusType selectBonusType() {
        BonusType type = BonusType.GOLD;

        for (BonusType type1 : bonusPriorities.keySet()) {
            if (bonusPriorities.get(type) < bonusPriorities.get(type1) && CooldownsManager.getBonusCooldown(type1) == 0) {
                type = type1;
            }
        }

//        log.debug("type = " + type);
        CooldownsManager.decrementBonusCooldowns();
        CooldownsManager.resetBonusCooldown(type);
        return type;
    }

    private void initPotPriorities() {
        potsPriorities = new HashMap<PotType, Integer>();
        potsPriorities.put(PotType.SIMPLE, 0);
        potsPriorities.put(PotType.LARGE, 1);
        potsPriorities.put(PotType.IRON, 2);
        potsPriorities.put(PotType.CAT, 3);
        potsPriorities.put(PotType.EXPLOSIVE, 4);
        potsPriorities.put(PotType.BONUS, 5);
    }

    private void initBonusPriorities() {
        bonusPriorities = new HashMap<BonusType, Integer>();
        bonusPriorities.put(BonusType.GOLD, 0);
        bonusPriorities.put(BonusType.SPEED, 1);
        bonusPriorities.put(BonusType.ARMOR, 2);
        bonusPriorities.put(BonusType.TIME_DECELERATION, 3);
        bonusPriorities.put(BonusType.LIFE, 4);
    }
}
