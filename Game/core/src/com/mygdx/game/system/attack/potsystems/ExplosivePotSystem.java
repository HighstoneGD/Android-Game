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

public class ExplosivePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;
    private PooledEngine engine;

    public ExplosivePotSystem(int x, int y, PooledEngine engine) {
        this.x = x;
        this.y = y;
        this.engine = engine;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);
        boolean left = true;
        boolean up = true;

        if (x == 0) {
            left = false;
        }

        if (y == 0) {
            up = false;
        }

        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
            } else if (number.xNumber == x) {

                if (up && number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                } else if (!up && number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                }

            } else if (left && number.xNumber == x - 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                } else if (up && number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                } else if (!up && number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                }

            } else if (!left && number.xNumber == x + 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                } else if (up && number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                } else if (!up && number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.EXPLOSIVE_EXISTANCE_TIME);
                }

            }

        }
    }
}
