package com.mygdx.game.util.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.util.objects.Bonus;
import com.mygdx.game.common.enums.BonusType;
import com.mygdx.game.util.objects.DamageObject;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;

public class ObjectCreator {

    private static final Logger log = new Logger(ObjectCreator.class.getName(), Logger.DEBUG);

    public static void createDamageObject(Entity cell, int dam, float time) {
        AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
        attackState.timers.add(new DamageObject(dam, time));
    }

    public static void createBonusObject(Entity cell, float time, BonusType type) {
        BonusComponent bonus = Mappers.BONUS.get(cell);
        bonus.timers.add(new Bonus(time, type));
    }

    private ObjectCreator() {
    }

}
