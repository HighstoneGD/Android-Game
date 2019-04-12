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

public class CatSystem extends EntitySystem {

    private EndlessModeScreen screen;
    private static final Logger log = new Logger(CatSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    public CatSystem(EndlessModeScreen screen) {
        this.screen = screen;
    }

    public void attack(int x) {
        log.debug("cat starts attacking");
        try {
            Thread.sleep(GameConfig.CAT_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == 0) {
                log.debug("target selected: " + number.xNumber + " " + number.yNumber);
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.timers.add(new DamageObject(GameConfig.CAT_DAMAGE, GameConfig.CAT_STAY_TIME));
                BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                bounds.color = Color.RED;
            }
        }

        Direction direction = new Direction();
        if (x == 0) {
            direction.direction = Directions.RIGHT;
        } else if (x == screen.x - 1) {
            direction.direction = Directions.LEFT;
        }
        jump(x, 0, direction);
    }

    private void jump(int x, int y, Direction direction) {
        log.debug("the cat is currently on " + x + " " + y + " moves on " + direction.direction);
        try {
            Thread.sleep(GameConfig.CAT_JUMP_TIME);
        } catch (Exception e) {}

        if (y == screen.y - 1) {
            return;
        } else if (x == 0 || x == screen.x - 1) {
            direction.changeDirection();
        }

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);

        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.yNumber == y + 1) {
                if (direction.direction == Directions.LEFT && number.xNumber == x - 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(GameConfig.CAT_DAMAGE, GameConfig.CAT_STAY_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                    jump(x - 1, y + 1, direction);
                } else if (direction.direction == Directions.RIGHT && number.xNumber == x + 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(GameConfig.CAT_DAMAGE, GameConfig.CAT_STAY_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                    jump(x + 1, y + 1, direction);
                }
            }
        }
    }
}
