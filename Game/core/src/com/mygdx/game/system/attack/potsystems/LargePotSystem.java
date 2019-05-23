package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.screen.BasicGameScreen;

public class LargePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;
    private PooledEngine engine;
    private EntityFactory factory;

    public LargePotSystem(int x, int y, BasicGameScreen screen) {
        this.x = x;
        this.y = y;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
    }

    @Override
    public void run() {
        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);
        float cellX = 0;
        float cellY = 0;
        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                PositionComponent position = Mappers.POSITION.get(cell);
                cellX = position.x;
                cellY = position.y;
            }
        }

        factory.addPot(PotType.LARGE, cellX, x, y);
        factory.addShadow(cellX, cellY, Constants.POT_FLIGHT_TIME / 1000f);

        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.LARGE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            }

            if (number.xNumber == x - 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            }

            if (number.xNumber == x + 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            }

        }
    }
}
