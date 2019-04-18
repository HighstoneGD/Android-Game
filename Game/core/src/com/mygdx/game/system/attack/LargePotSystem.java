package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.objects.DamageObject;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;

public class LargePotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            AttackStateComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;

    public LargePotSystem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.LARGE_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x) {

                if (number.yNumber == y) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.LARGE_CENTRAL_DAMAGE, Constants.LARGE_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (number.yNumber == y - 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (number.yNumber == y + 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                }

            }

            if (number.xNumber == x - 1) {

                if (number.yNumber == y) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (number.yNumber == y - 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (number.yNumber == y + 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                }

            }

            if (number.xNumber == x + 1) {

                if (number.yNumber == y) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (number.yNumber == y - 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                } else if (number.yNumber == y + 1) {
                    AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                    attackState.timers.add(new DamageObject(Constants.SHARD_DAMAGE, Constants.SHARD_EXISTANCE_TIME));
                    BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                    bounds.color = Color.RED;
                }

            }

        }
    }
}
