package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.screen.BasicGameScreen;

import java.util.HashMap;
import java.util.Map;

public class AttackSystem extends IntervalSystem {

    private static final Logger log = new Logger(AttackSystem.class.getName(), Logger.DEBUG);
    private BasicGameScreen screen;
    private Map<PotType, Integer> priorities;
    private PooledEngine engine;

    public AttackSystem(float attackSpeed, BasicGameScreen screen, PooledEngine engine) {
        super(attackSpeed);
        this.screen = screen;
        priorities = new HashMap<PotType, Integer>();
        priorities.put(PotType.SIMPLE, 0);
        priorities.put(PotType.LARGE, 1);
        priorities.put(PotType.IRON, 2);
        priorities.put(PotType.CAT, 3);
        priorities.put(PotType.EXPLOSIVE, 4);
        this.engine = engine;
    }

    @Override
    protected void updateInterval() {
        PotType type = selectPotType();
        int x = getEngine().getSystem(TargetSystem.class).selectTargetX();
        int y = getEngine().getSystem(TargetSystem.class).selectTargetY();

        if (type == PotType.SIMPLE) {
            SimplePotSystem simplePotSystem = new SimplePotSystem(x, y, engine);
            getEngine().addSystem(simplePotSystem);
            Thread thread = new Thread(simplePotSystem);
            thread.start();
        } else if (type == PotType.LARGE) {
            LargePotSystem largePotSystem = new LargePotSystem(x, y, engine);
            getEngine().addSystem(largePotSystem);
            Thread thread = new Thread(largePotSystem);
            thread.start();
        } else if (type == PotType.EXPLOSIVE) {
            ExplosivePotSystem explosivePotSystem = new ExplosivePotSystem(x, y, engine);
            getEngine().addSystem(explosivePotSystem);
            Thread thread = new Thread(explosivePotSystem);
            thread.start();
        } else if (type == PotType.IRON) {
            IronPotSystem ironPotSystem = new IronPotSystem(x, y, engine);
            getEngine().addSystem(ironPotSystem);
            Thread thread = new Thread(ironPotSystem);
            thread.start();
        } else if (type == PotType.CAT) {
            CatSystem catSystem = new CatSystem(screen, x, engine);
            getEngine().addSystem(catSystem);
            Thread thread = new Thread(catSystem);
            thread.start();
        }
    }

    private PotType selectPotType() {
        PotType type = PotType.SIMPLE;

        for (PotType type1 : priorities.keySet()) {
            if (priorities.get(type) < priorities.get(type1) && GameManager.COOLDOWNS_MANAGER.getCooldown(type1) == 0) {
                type = type1;
            }
        }

        log.debug("type = " + type);
        GameManager.COOLDOWNS_MANAGER.decrementCooldowns();
        GameManager.COOLDOWNS_MANAGER.resetCooldown(type);
        return type;
    }
}
