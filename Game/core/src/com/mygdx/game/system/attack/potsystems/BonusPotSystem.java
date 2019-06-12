package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.util.logic.NumberConverter;
import com.mygdx.game.util.logic.ObjectCreator;

public class BonusPotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            PositionOnGridComponent.class,
            BonusComponent.class
    ).get();

    private BasicGameScreen screen;

    private final int x;
    private final int y;
    private final BonusType type;
    private final PooledEngine engine;
    private final EntityFactory factory;

    public BonusPotSystem(int x, int y, BasicGameScreen screen, BonusType type) {
        this.screen = screen;
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

        screen.potThrown();

        for (Entity cell : cells) {
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(cell);

            if (positionOnGrid.xNumber == x && positionOnGrid.yNumber == y) {
                ObjectCreator.createDamageObject(cell, Constants.BONUS_DAMAGE, Constants.POT_EXISTANCE_TIME);
                ObjectCreator.createBonusObject(cell, Constants.BONUS_EXISTANCE_TIME, type);
                return;
            }
        }
    }
}
