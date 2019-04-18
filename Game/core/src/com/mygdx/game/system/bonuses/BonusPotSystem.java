package com.mygdx.game.system.bonuses;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.Bonus;
import com.mygdx.game.common.objects.BonusType;
import com.mygdx.game.common.objects.DamageObject;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.NumberComponent;

public class BonusPotSystem extends EntitySystem implements Runnable {

    private static final Family FAMILY = Family.all(
            NumberComponent.class,
            BonusComponent.class,
            BoundsComponent.class
    ).get();

    private final int x;
    private final int y;
    private final BonusType type;
    private static final Logger log = new Logger(BonusPotSystem.class.getName(), Logger.DEBUG);

    public BonusPotSystem(int x, int y, BonusType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Constants.BONUS_FLIGHT_TIME);
        } catch (Exception e) {
            return;
        }

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(FAMILY);

        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);

            if (number.xNumber == x && number.yNumber == y) {

                AttackStateComponent attackState = Mappers.ATTACK_STATE.get(cell);
                attackState.timers.add(new DamageObject(Constants.BONUS_DAMAGE, Constants.SIMPLE_EXISTANCE_TIME));
                BonusComponent bonus = Mappers.BONUS.get(cell);
                bonus.timers.add(new Bonus(Constants.BONUS_EXISTANCE_TIME, type));
                BoundsComponent bounds = Mappers.BOUNDS.get(cell);
                bounds.color = Color.GOLD;
                return;

            }
        }
    }
}
