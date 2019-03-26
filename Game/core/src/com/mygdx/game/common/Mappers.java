package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.MovementStateComponent;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;

public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<MovementStateComponent> MOVEMENT_STATE =
            ComponentMapper.getFor(MovementStateComponent.class);

    public static final ComponentMapper<PositionOnGridComponent> POSITION_ON_GRID =
            ComponentMapper.getFor(PositionOnGridComponent.class);

    private Mappers() {}
}
