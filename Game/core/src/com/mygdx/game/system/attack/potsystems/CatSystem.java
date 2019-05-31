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
import com.mygdx.game.common.objects.DamageObject;
import com.mygdx.game.common.objects.Direction;
import com.mygdx.game.common.objects.Directions;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.util.NumberConverter;

@Deprecated
public class CatSystem extends EntitySystem implements Runnable {

    private BasicGameScreen screen;
    private static final Logger log = new Logger(CatSystem.class.getName(), Logger.DEBUG);
    private int x;
    private PooledEngine engine;
    private EntityFactory factory;

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    public CatSystem(BasicGameScreen screen, int x) {
        this.screen = screen;
        this.x = x;
        this.engine = screen.getEngine();
        this.factory = screen.getFactory();
    }

    @Override
    public void run() {
        float cellX = engine.getSystem(NumberConverter.class).getCoordinates(x, 0).x;
        float cellY = engine.getSystem(NumberConverter.class).getCoordinates(x, 0).y;

        factory.addPot(PotType.CAT, cellX, cellY, x, 0);

        try {
            Thread.sleep(Constants.POT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        Direction direction = new Direction();

        for (int i = 0; i < screen.y; i++) {

            if (direction.direction == Directions.LEFT && x == 0) {
                direction.changeDirection();
            } else if (direction.direction == Directions.RIGHT && x == screen.x - 1) {
                direction.changeDirection();
            }

            if (direction.direction == Directions.LEFT) {
                x--;
                jumpOn(x, i);
            } else if (direction.direction == Directions.RIGHT) {
                x++;
                jumpOn(x, i);
            }
        }
    }

    private void jumpOn(int x, int y) {
        ImmutableArray<Entity> cells = engine.getEntitiesFor(FAMILY);
        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.timers.add(new DamageObject(Constants.CAT_DAMAGE, Constants.POT_EXISTANCE_TIME));
                BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                bounds.color = Color.RED;

                try {
                    Thread.sleep(Constants.CAT_JUMP_TIME);
                } catch (Exception e) {
                    return;
                }
            }
        }
    }
}
