package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.component.AttackStateComponent;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionOnGridComponent;

public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<MovementStateComponent> MOVEMENT_STATE =
            ComponentMapper.getFor(MovementStateComponent.class);

    public static final ComponentMapper<PositionOnGridComponent> POSITION_ON_GRID =
            ComponentMapper.getFor(PositionOnGridComponent.class);

    public static final ComponentMapper<NumberComponent> NUMBER =
            ComponentMapper.getFor(NumberComponent.class);

    public static final ComponentMapper<AttackStateComponent> ATTACK_STATE =
            ComponentMapper.getFor(AttackStateComponent.class);

    public static final ComponentMapper<BonusComponent> BONUS =
            ComponentMapper.getFor(BonusComponent.class);

    private Mappers() {
    }
}
