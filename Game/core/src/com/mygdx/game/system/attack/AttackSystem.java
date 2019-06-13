package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.util.logic.SystemCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AttackSystem extends IntervalSystem {

    private static final Logger log = new Logger(AttackSystem.class.getName(), Logger.DEBUG);
    private BasicGameScreen screen;
    private List<PotType> potTypes;
    private Map<PotType, Integer> potsPriorities;
    private PooledEngine engine;

    public AttackSystem(float attackSpeed, BasicGameScreen screen, List<PotType> potTypes) {
        super(attackSpeed);
        this.screen = screen;
        this.potTypes = potTypes;

        initPotPriorities();

        this.engine = screen.getEngine();
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
            SystemCreator.createExplosivePotSystem(engine, screen, x, y);
        } else if (type == PotType.IRON) {
            SystemCreator.createIronPotSystem(engine, screen, x, y);
        } else if (type == PotType.BONUS) {
            SystemCreator.createBonusPotSystem(engine, screen, selectBonusType(), x, y);
        }
    }

    private PotType selectPotType() {
        PotType type = PotType.SIMPLE;

        for (PotType type1 : potsPriorities.keySet()) {
            if (potsPriorities.get(type) < potsPriorities.get(type1) && GameManager.INSTANCE.getPotCooldown(type1) == 0) {
                type = type1;
            }
        }

        GameManager.INSTANCE.decrementCooldowns();
        GameManager.INSTANCE.resetCooldown(type);
        return type;
    }

    private BonusType selectBonusType() {
        Random random = new Random();
        int k = random.nextInt(4);

        if (k == 0) {
            return BonusType.LIFE;
        } else if (k == 1) {
            return BonusType.SPEED_UP;
        } else {
            return BonusType.ARMOR;
        }
    }

    private void initPotPriorities() {
        potsPriorities = new HashMap<PotType, Integer>();

        for (int i = 0; i < potTypes.size(); i++) {
            potsPriorities.put(potTypes.get(i), i);
        }
    }
}
