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
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.util.NumberConverter;
import com.mygdx.game.util.ObjectCreator;

public class ExplosivePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;
    private boolean left;
    private boolean up;
    private PooledEngine engine;
    private EntityFactory factory;

    public ExplosivePotSystem(int x, int y, BasicGameScreen screen) {
        this.x = x;
        this.y = y;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
        left = true;
        up = true;

        if (x == 0) {
            left = false;
        }

        if (y == 0) {
            up = false;
        }
    }

    @Override
    public void run() {
        getEngine().getSystem(GranRenderSystem.class).throwPot(PotType.EXPLOSIVE);

        try {
            Thread.sleep(800);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);
        float cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, y, left, up).x;
        float cellY = engine.getSystem(NumberConverter.class).getCoordinates(x, y, left, up).y;
        factory.addPot(PotType.EXPLOSIVE, cellX, cellY, x, y);

        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
            } else if (number.xNumber == x) {

                if (up && number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (!up && number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            } else if (left && number.xNumber == x - 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (up && number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (!up && number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            } else if (!left && number.xNumber == x + 1) {

                if (number.yNumber == y) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (up && number.yNumber == y - 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                } else if (!up && number.yNumber == y + 1) {
                    ObjectCreator.createDamageObject(cell, Constants.EXPLOSIVE_CENTRAL_DAMAGE, Constants.POT_EXISTANCE_TIME);
                }

            }

        }
    }
}
