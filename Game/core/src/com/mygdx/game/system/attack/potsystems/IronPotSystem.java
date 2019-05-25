package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.common.Constants;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.util.NumberConverter;
import com.mygdx.game.util.ObjectCreator;

public class IronPotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;
    private PooledEngine engine;
    private EntityFactory factory;

    public IronPotSystem(int x, int y, BasicGameScreen screen) {
        this.x = x;
        this.y = y;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
    }

    @Override
    public void run() {
        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);
        float cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, y).x;
        float cellY = engine.getSystem(NumberConverter.class).getCoordinates(x, y).y;

        factory.addPot(PotType.IRON, cellX, cellY, x, y);

        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME / 2);
        } catch (Exception e) {
            return;
        }

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                ObjectCreator.createDamageObject(cell, Constants.IRON_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
            }

        }
    }
}
