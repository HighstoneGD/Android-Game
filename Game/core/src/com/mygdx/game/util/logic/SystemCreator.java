package com.mygdx.game.util.logic;

import com.badlogic.ashley.core.PooledEngine;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.attack.potsystems.BonusPotSystem;
import com.mygdx.game.system.attack.potsystems.ExplosivePotSystem;
import com.mygdx.game.system.attack.potsystems.IronPotSystem;
import com.mygdx.game.system.attack.potsystems.LargePotSystem;
import com.mygdx.game.system.attack.potsystems.SimplePotSystem;

public class SystemCreator {

    public static void createSimplePotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        engine.addSystem(new SimplePotSystem(x, y, screen));
    }

    public static void createIronPotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        engine.addSystem(new IronPotSystem(x, y, screen));
    }

    public static void createLargePotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        engine.addSystem(new LargePotSystem(x, y, screen));
    }

    public static void createExplosivePotSystem(PooledEngine engine, BasicGameScreen screen, int x, int y) {
        engine.addSystem(new ExplosivePotSystem(x, y, screen));
    }

    public static void createBonusPotSystem(PooledEngine engine, BasicGameScreen screen, BonusType type, int x, int y) {
        engine.addSystem(new BonusPotSystem(x, y, screen, type));
    }

    private SystemCreator() {
    }

}
