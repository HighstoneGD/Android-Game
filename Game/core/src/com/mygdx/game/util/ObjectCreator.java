package com.mygdx.game.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.Bonus;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.DamageObject;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;

public class ObjectCreator {

    public static void createDamageObject(Entity cell, int dam, float time) {
        AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
        attackState.timers.add(new DamageObject(dam, time));
        BoundsComponent bounds = Mappers.BOUNDS.get(cell);
        bounds.color = Color.RED;
    }

    public static void createBonusObject(Entity cell, float time, BonusType type) {
        BonusComponent bonus = Mappers.BONUS.get(cell);
        bonus.timers.add(new Bonus(time, type));
        BoundsComponent bounds = Mappers.BOUNDS.get(cell);
        bounds.color = Color.GOLD;
    }

    private ObjectCreator() {
    }

}
