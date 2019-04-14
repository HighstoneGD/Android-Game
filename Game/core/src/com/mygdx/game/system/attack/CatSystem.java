package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.DamageObject;
import com.mygdx.game.common.Direction;
import com.mygdx.game.common.Directions;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.screen.game.EndlessModeScreen;

public class CatSystem extends EntitySystem implements Runnable {

    private EndlessModeScreen screen;
    private static final Logger log = new Logger(CatSystem.class.getName(), Logger.DEBUG);
    private int x;

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    public CatSystem(EndlessModeScreen screen, int x) {
        this.screen = screen;
        this.x = x;
    }

    @Override
    public void run() {
        log.debug("cat starts attacking");
        try {
            Thread.sleep(GameConfig.CAT_FLIGHT_TIME);
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
        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);
        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.timers.add(new DamageObject(GameConfig.CAT_DAMAGE, GameConfig.CAT_STAY_TIME));
                BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                bounds.color = Color.RED;

                try {
                    Thread.sleep(GameConfig.CAT_JUMP_TIME);
                } catch (Exception e) {
                    return;
                }
            }
        }
    }
}
