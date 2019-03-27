package com.mygdx.game.system.attack;

import com.badlogic.ashley.systems.IntervalSystem;
import com.mygdx.game.debug.PotType;
import com.mygdx.game.screen.game.EndlessModeScreen;

import java.util.Random;

public class PotSpawnSystem extends IntervalSystem {

    private final AttackListener listener;

    public PotSpawnSystem(EndlessModeScreen screen, AttackListener listener) {
        super(screen.potSpawnSpeed);
        this.listener = listener;
    }

    @Override
    protected void updateInterval() {
        int cellsAmount = 25;
        Random random = new Random();
        int attackedCell = random.nextInt(cellsAmount);

        PotType type = PotType.SIMPLE;

        listener.attack(type, attackedCell);
    }
}
