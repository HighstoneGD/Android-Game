package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.util.NumberConverter;
import com.mygdx.game.util.ObjectCreator;

public class SimplePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private static final Logger log = new Logger(SimplePotSystem.class.getName(), Logger.DEBUG);

    private final int x;
    private final int y;
    private PooledEngine engine;
    private EntityFactory factory;

    public SimplePotSystem(int x, int y, BasicGameScreen screen) {
        this.x = x;
        this.y = y;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
    }

    @Override
    public void run() {
        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);
        float cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, y).x;

        factory.addPot(PotType.SIMPLE, cellX, x, y);

        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.SIMPLE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            }

            if (number.yNumber == y) {

                if (number.xNumber == x - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (number.xNumber == x + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.SHARD_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            }
        }
    }
}
