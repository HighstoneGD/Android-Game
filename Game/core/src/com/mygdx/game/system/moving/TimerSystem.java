package com.mygdx.game.system.moving;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;

public class TimerSystem extends EntitySystem implements Runnable {

    public TimerSystem() {}

    private static final Family PLAYER_FAMILY = Family.all(
            MovementStateComponent.class
    ).get();

    @Override
    public void run() {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY);

        for (int i = 0; i < players.size(); i++) {
            Entity player = players.get(i);
            MovementStateComponent movementState = Mappers.MOVEMENT_STATE.get(player);
            movementState.setMoving(true);
            waitMillis(Constants.PLAYER_JUMP_TIME);
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
