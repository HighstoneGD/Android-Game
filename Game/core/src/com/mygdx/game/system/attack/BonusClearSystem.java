package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.BonusComponent;

public class BonusClearSystem extends IteratingSystem {

    private static final Family CELLS = Family.all(
            BonusComponent.class
    ).get();

    public BonusClearSystem() {
        super(CELLS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BonusComponent bonusComponent = Mappers.BONUS.get(entity);

        for (int i = 0; i < bonusComponent.timers.size(); i++) {
            try {
                bonusComponent.timers.get(i).reduceTimer(deltaTime);
                if (bonusComponent.timers.get(i).time == 0) {
                    bonusComponent.timers.remove(bonusComponent.timers.get(i));
                }
            } catch (NullPointerException e) {
            }
        }
    }

}
