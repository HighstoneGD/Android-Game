package com.mygdx.game.system.moving;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.system.debug.PositionsCalculationSystem;

public class BoundsSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            BoundsComponent.class,
            PlayerComponent.class,
            PositionOnGridComponent.class
    ).get();

    public BoundsSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);


        try {
            bounds.bounds.x = getEngine().getSystem(PositionsCalculationSystem.class).positions[positionOnGrid.xNumber][positionOnGrid.yNumber][0];
        } catch (Exception e) {}
        try {
            bounds.bounds.y = getEngine().getSystem(PositionsCalculationSystem.class).positions[positionOnGrid.xNumber][positionOnGrid.yNumber][1];
        } catch (Exception e) {}
    }
}
