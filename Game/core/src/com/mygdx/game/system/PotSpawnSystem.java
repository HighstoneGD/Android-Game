package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.screen.game.EndlessModeScreen;

import java.util.Random;

public class PotSpawnSystem extends IntervalSystem {

    private static final Logger log = new Logger(PotSpawnSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class
    ).get();

    public PotSpawnSystem(EndlessModeScreen screen) {
        super(screen.potSpawnSpeed);
    }

    @Override
    protected void updateInterval() {
        int cellsAmount = 25;
        Random random = new Random();
        int attackedCell = random.nextInt(cellsAmount);

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.number == attackedCell) {
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.underAttack = true;
            }
        }
    }
}
