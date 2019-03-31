package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.component.PlayerComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.debug.OldCellsPositions;

/**
 * Created by goran on 6/09/2016.
 */
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
            bounds.bounds.x = OldCellsPositions.getX(positionOnGrid.number);
        } catch (Exception e) {}
        try {
            bounds.bounds.y = OldCellsPositions.getY(positionOnGrid.number);
        } catch (Exception e) {}
    }
}
