package com.mygdx.game.system.control;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.component.marking.PlayerComponent;

public class PlayerPresenceSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            CellComponent.class,
            PositionOnGridComponent.class
    ).get();

    private static final Family PLAYER = Family.all(
            PositionOnGridComponent.class,
            PlayerComponent.class
    ).get();

    public PlayerPresenceSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CellComponent cellComponent = Mappers.CELL_COMPONENT.get(entity);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);

        cellComponent.hasPlayer = false;

        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER);
        PositionOnGridComponent playerPosition = Mappers.POSITION_ON_GRID.get(players.first());

        if (positionOnGrid.xNumber == playerPosition.xNumber && positionOnGrid.yNumber == playerPosition.yNumber) {
            cellComponent.hasPlayer = true;
        }
    }
}
