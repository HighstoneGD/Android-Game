package com.mygdx.game.util.logic;

import com.badlogic.ashley.core.PooledEngine;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.attack.potsystems.BonusPotSystem;
import com.mygdx.game.system.attack.potsystems.ExplosivePotSystem;
import com.mygdx.game.system.attack.potsystems.IronPotSystem;
import com.mygdx.game.system.attack.potsystems.LargePotSystem;
import com.mygdx.game.system.attack.potsystems.SimplePotSystem;
import com.mygdx.game.system.control.TimerSystem;

public class SystemCreator {

    public static void createSimplePotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        SimplePotSystem simplePotSystem = new SimplePotSystem(x, y, screen);
        engine.addSystem(simplePotSystem);
        Thread thread = new Thread(simplePotSystem);
        thread.start();
    }

    public static void createIronPotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        IronPotSystem ironPotSystem = new IronPotSystem(x, y, screen);
        engine.addSystem(ironPotSystem);
        Thread thread = new Thread(ironPotSystem);
        thread.start();
    }

    public static void createLargePotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        LargePotSystem largePotSystem = new LargePotSystem(x, y, screen);
        engine.addSystem(largePotSystem);
        Thread thread = new Thread(largePotSystem);
        thread.start();
    }

    public static void createExplosivePotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        ExplosivePotSystem explosivePotSystem = new ExplosivePotSystem(x, y, screen);
        engine.addSystem(explosivePotSystem);
        Thread thread = new Thread(explosivePotSystem);
        thread.start();
    }

    public static void createBonusPotSystem(PooledEngine engine, BasicGameScreen screen, BonusType type, int x, int y) {
        BonusPotSystem bonusPotSystem = new BonusPotSystem(x, y, screen, type);
        engine.addSystem(bonusPotSystem);
        Thread thread = new Thread(bonusPotSystem);
        thread.start();
    }

    public static void createTimer(PooledEngine engine) {
        TimerSystem timerSystem = new TimerSystem();
        engine.addSystem(timerSystem);
        Thread thread = new Thread(timerSystem);
        thread.start();
    }

    private SystemCreator() {
    }

}
