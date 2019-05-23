package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.Bonus;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.DamageObject;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.screen.BasicGameScreen;

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

        factory.addPot(PotType.BONUS, cellX, x, y);
        factory.addShadow(cellX, cellY, Constants.POT_FLIGHT_TIME / 1000f);

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
