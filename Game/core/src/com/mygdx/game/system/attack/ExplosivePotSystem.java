package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.common.DamageObject;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.debug.GameConfig;
import com.mygdx.game.screen.game.EndlessModeScreen;

public class ExplosivePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;

    public ExplosivePotSystem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(GameConfig.EXPLOSIVE_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);
        boolean left = true;
        boolean up = true;

        if (x == 0) {
            left = false;
        }

        if (y == 0) {
            up = false;
        }

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.timers.add(new DamageObject(GameConfig.EXPLOSIVE_CENTRAL_DAMAGE, GameConfig.EXPLOSIVE_EXISTANCE_TIME));
                BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                bounds.color = Color.RED;
            } else if (left && number.xNumber == x - 1) {

                if (up && number.yNumber == y - 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(GameConfig.EXPLOSIVE_CENTRAL_DAMAGE, GameConfig.EXPLOSIVE_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (!up && number.yNumber == y + 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(GameConfig.EXPLOSIVE_CENTRAL_DAMAGE, GameConfig.EXPLOSIVE_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                }

            } else if (!left && number.xNumber == x + 1) {

                if (up && number.yNumber == y - 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(GameConfig.EXPLOSIVE_CENTRAL_DAMAGE, GameConfig.EXPLOSIVE_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (!up && number.yNumber == y + 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(GameConfig.EXPLOSIVE_CENTRAL_DAMAGE, GameConfig.EXPLOSIVE_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                }

            }

        }
    }
}
