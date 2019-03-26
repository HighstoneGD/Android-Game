package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.MovementStateComponent;

public class TimerSystem extends EntitySystem {

    private static PooledEngine engine;

    public TimerSystem(PooledEngine engine) {
        this.engine = engine;
    }

    private static final Family FAMILY = Family.all(
            MovementStateComponent.class
    ).get();

    public static void startTimer() {
        ImmutableArray<Entity> players = engine.getEntitiesFor(FAMILY);

        for (Entity player : players) {
            MovementStateComponent movementState = Mappers.MOVEMENT_STATE.get(player);
            movementState.setMoving(true);
            waitMillis(300);
            movementState.setMoving(false);
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
