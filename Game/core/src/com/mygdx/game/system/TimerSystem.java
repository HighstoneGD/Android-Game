package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.debug.GameConfig;

public class TimerSystem extends EntitySystem {

    public TimerSystem() {}

    private static final Family PLAYER_FAMILY = Family.all(
            MovementStateComponent.class
    ).get();

    private static final Family CELLS_FAMILY = Family.all(
            AttackStateComponent.class,
            NumberComponent.class
    ).get();

    public void startTimer() {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY);

        for (Entity player : players) {
            MovementStateComponent movementState = Mappers.MOVEMENT_STATE.get(player);
            movementState.setMoving(true);
            waitMillis(300);
            movementState.setMoving(false);
        }
    }

    public void startAttackTimer(int damage, int x, int y) {
        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(CELLS_FAMILY);

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);
            if (number.xNumber == x && number.yNumber == y) {
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.damage += damage;
                waitMillis(GameConfig.SHARD_EXISTANCE_TIME);
                attackState.damage -= damage;
            }
        }
    }

    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
