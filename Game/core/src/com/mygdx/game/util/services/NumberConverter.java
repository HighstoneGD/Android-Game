package com.mygdx.game.util.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.util.objects.Coordinates;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.CellComponent;

public class NumberConverter extends EntitySystem {

    private static final Family CELLS = Family.all(
            CellComponent.class,
            PositionOnGridComponent.class,
            PositionComponent.class
    ).get();
    private ImmutableArray<Entity> cells;

    public Coordinates getCoordinates(int xNumber, int yNumber) {
        cells = getEngine().getEntitiesFor(CELLS);
        for (int i = 0; i < cells.size(); i++) {
            Entity entity = cells.get(i);
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);
            if (xNumber == positionOnGrid.xNumber && yNumber == positionOnGrid.yNumber) {
                PositionComponent position = Mappers.POSITION.get(entity);
                return new Coordinates(position.x, position.y);
            }
        }
        return new Coordinates(0, 0);
    }

    public Coordinates getCoordinates(int xNumber, int yNumber, boolean left, boolean up) {
        cells = getEngine().getEntitiesFor(CELLS);
        float startX = 0;
        float startY = 0;
        float endX = 0;
        float endY = 0;
        float middleX;
        float middleY;

        for (int i = 0; i < cells.size(); i++) {
            Entity entity = cells.get(i);
            PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);

            if (xNumber == positionOnGrid.xNumber && yNumber == positionOnGrid.yNumber) {
                PositionComponent position = Mappers.POSITION.get(entity);
                startX = position.x;
                startY = position.y;
            }

            if (left && up && positionOnGrid.xNumber == xNumber - 1 && positionOnGrid.yNumber == yNumber - 1) {
                PositionComponent position = Mappers.POSITION.get(entity);
                endX = position.x;
                endY = position.y;
            } else if (left && !up && positionOnGrid.xNumber == xNumber - 1 && positionOnGrid.yNumber == yNumber + 1) {
                PositionComponent position = Mappers.POSITION.get(entity);
                endX = position.x;
                endY = position.y;
            } else if (!left && up && positionOnGrid.xNumber == xNumber + 1 && positionOnGrid.yNumber == yNumber - 1) {
                PositionComponent position = Mappers.POSITION.get(entity);
                endX = position.x;
                endY = position.y;
            } else if (!left && !up && positionOnGrid.xNumber == xNumber + 1 && positionOnGrid.yNumber == yNumber + 1) {
                PositionComponent position = Mappers.POSITION.get(entity);
                endX = position.x;
                endY = position.y;
            }
        }

        middleX = (startX + endX) / 2f;
        middleY = (startY + endY) / 2f;

        return new Coordinates(middleX, middleY);
    }
}
