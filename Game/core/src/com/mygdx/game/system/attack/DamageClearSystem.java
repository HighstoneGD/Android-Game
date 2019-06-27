package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;

public class DamageClearSystem extends IteratingSystem {

    private static final Family CELLS = Family.all(
            AttackStateComponent.class
    ).get();

    public DamageClearSystem() {
        super(CELLS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AttackStateComponent attackState = Mappers.ATTACK_STATE.get(entity);

        for (int i = 0; i < attackState.timers.size(); i++) {
            try {
                attackState.timers.get(i).reduceTimer(deltaTime);
                if (attackState.timers.get(i).time == 0) {
                    attackState.timers.remove(attackState.timers.get(i));
                }
            } catch (NullPointerException e) {
            }
        }
    }
}
