package com.mygdx.game.system.moving;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.component.marking.PlayerComponent;

public class PlayerPresenseSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            CellComponent.class,
            NumberComponent.class
    ).get();

    private static final Family PLAYER = Family.all(
            PositionOnGridComponent.class,
            PlayerComponent.class
    ).get();

    public PlayerPresenseSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CellComponent cellComponent = Mappers.CELL_COMPONENT.get(entity);
        NumberComponent number = Mappers.NUMBER.get(entity);

        cellComponent.hasPlayer = false;

        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(players.first());

        if (positionOnGrid.xNumber == number.xNumber && positionOnGrid.yNumber == number.yNumber) {
            cellComponent.hasPlayer = true;
        }
    }
}
