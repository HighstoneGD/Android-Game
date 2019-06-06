package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.util.objects.DamageObject;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.controlling.AvoidedPotsManager;
import com.mygdx.game.controlling.HealthManager;

public class DamageOnCellSystem extends IteratingSystem {

    private static final Family CELLS = Family.all(
            AttackStateComponent.class
    ).get();

    private static final Logger log = new Logger(DamageOnCellSystem.class.getName(), Logger.DEBUG);

    public DamageOnCellSystem() {
        super(CELLS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CellComponent cellComponent = Mappers.CELL_COMPONENT.get(entity);
        if (cellComponent.hasPlayer) {
            AttackStateComponent attackState = Mappers.ATTACK_STATE.get(entity);

            for (int i = 0; i < attackState.timers.size(); i++) {
                DamageObject damageObject = attackState.timers.get(i);
                HealthManager.takeDamage(damageObject.damage);
                AvoidedPotsManager.resetAvoided();
            }

            attackState.timers.clear();
            return;
        }
    }
}
