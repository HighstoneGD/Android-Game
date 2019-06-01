package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.util.NumberConverter;
import com.mygdx.game.util.ObjectCreator;

public class BonusPotSystem extends EntitySystem implements Runnable {

    private final int x;
    private final int y;
    private final BonusType type;
    private final PooledEngine engine;
    private final EntityFactory factory;
    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            BonusComponent.class,
            BoundsComponent.class
    ).get();

    public BonusPotSystem(int x, int y, BasicGameScreen screen, BonusType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
    }

    @Override
    public void run() {
        getEngine().getSystem(GranRenderSystem.class).throwPot(PotType.SIMPLE);

        try {
            Thread.sleep(800);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);

        float cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, y).x;
        float cellY = engine.getSystem(NumberConverter.class).getCoordinates(x, y).y;

        factory.addPot(PotType.BONUS, cellX, cellY, x, y);

        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                ObjectCreator.createDamageObject(cell, Constants.BONUS_DAMAGE, Constants.BONUS_EXISTANCE_TIME);
                ObjectCreator.createBonusObject(cell, Constants.BONUS_EXISTANCE_TIME, type);
                return;
            }
        }
    }
}
