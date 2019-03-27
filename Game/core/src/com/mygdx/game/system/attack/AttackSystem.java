package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.debug.PotType;

public class AttackSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            AttackStateComponent.class,
            NumberComponent.class
    ).get();

    public AttackSystem() {
        super();
    }

    public void doDamage(PotType type, int cell) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);
        for (Entity entity : entities) {
            NumberComponent number = Mappers.NUMBER.get(entity);

            if (number.number == cell) {
                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(entity);
                attackState.damage = type.getDamage();

                if (type == PotType.SIMPLE) {
                    if (cell % 5 != 0) {
                        doDamage(PotType.SHARD, cell - 1);
                    }
                    if (cell % 5 != 4) {
                        doDamage(PotType.SHARD, cell + 1);
                    }
                    if (cell > 4) {
                        doDamage(PotType.SHARD, cell - 5);
                    }
                    if (cell < 20) {
                        doDamage(PotType.SHARD, cell + 5);
                    }
                }

            }
        }

    }
}
