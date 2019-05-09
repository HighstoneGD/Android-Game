package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;

public class LargePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;
    private PooledEngine engine;

    public LargePotSystem(int x, int y, PooledEngine engine) {
        this.x = x;
        this.y = y;
        this.engine = engine;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.LARGE_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.LARGE_CENTRAL_DAMAGE, Constants.LARGE_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                }

            }

            if (number.xNumber == x - 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                }

            }

            if (number.xNumber == x + 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME);
                }

            }

        }
    }
}
