package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.objects.DamageObject;
import com.mygdx.game.common.objects.Direction;
import com.mygdx.game.common.objects.Directions;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.common.Constants;
import com.mygdx.game.screen.BasicGameScreen;

public class CatSystem extends EntitySystem implements Runnable {

    private BasicGameScreen screen;
    private static final Logger log = new Logger(CatSystem.class.getName(), Logger.DEBUG);
    private int x;
    private PooledEngine engine;

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    public CatSystem(BasicGameScreen screen, int x, PooledEngine engine) {
        this.screen = screen;
        this.x = x;
        this.engine = engine;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.CAT_FLIGHT_TIME);
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
                attackState.timers.add(new DamageObject(Constants.CAT_DAMAGE, Constants.CAT_STAY_TIME));
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
