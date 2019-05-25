package com.mygdx.game.system.attack.potsystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.NumberComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.marking.PotComponent;
import com.mygdx.game.component.marking.CellComponent;
import com.mygdx.game.screen.BasicGameScreen;

public class DropPotsSystem extends IteratingSystem {

    private static final Family POTS = Family.all(
            PositionComponent.class,
            PotComponent.class
    ).get();

    private static final Family CELLS = Family.all(
            PositionComponent.class,
            CellComponent.class
    ).get();

    private static final Logger log = new Logger(DropPotsSystem.class.getName(), Logger.DEBUG);

    private BasicGameScreen screen;

    public DropPotsSystem(BasicGameScreen screen) {
        super(POTS);
        this.screen = screen;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(entity);

        float distance = 0;
        float cellY = 0;
        float cellX = 0;

        ImmutableArray<Entity> cells = getEngine().getEntitiesFor(CELLS);
        for (Entity cell : cells) {
            NumberComponent number = Mappers.NUMBER.get(cell);
            if (number.xNumber == positionOnGrid.xNumber && number.yNumber == positionOnGrid.yNumber) {
                PositionComponent cellPosition = Mappers.POSITION.get(cell);
                distance = Constants.WORLD_HEIGHT + 10f - cellPosition.y;
                cellY = cellPosition.y;
                cellX = cellPosition.x;
            }
        }

        int deltaY = screen.y - 1 - positionOnGrid.yNumber;
        float speed = distance / (Constants.POT_FLIGHT_TIME / 1000f);

        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);
        if (potComponent.type == PotType.IRON) {
            speed *= 2;
        }

        position.y -= deltaTime * speed;
        potComponent.progress = (Constants.WORLD_HEIGHT + 10f - position.y) / distance;

        if (position.y <= cellY) {
            smash(entity);
        }
    }

    private void smash(Entity entity) {
        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);
        screen.getFactory().addSmash(potComponent.type, potComponent.aimX, potComponent.aimY);
        getEngine().removeEntity(entity);
    }
}
