package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.OrderComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.PositionOnGridComponent;
import com.mygdx.game.component.marking.PlayerComponent;
import com.mygdx.game.component.marking.SmashComponent;
import com.mygdx.game.util.services.NumberConverter;

public class SmashesOrderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            OrderComponent.class,
            SmashComponent.class,
            PositionOnGridComponent.class
    ).get();

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionComponent.class
    ).get();

    public SmashesOrderSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(player.first());
        float playerY = getEngine().getSystem(NumberConverter.class).getCoordinates(positionOnGrid.xNumber, positionOnGrid.yNumber).y;

        PositionComponent position = Mappers.POSITION.get(entity);
        OrderComponent order = Mappers.ORDER.get(entity);

        if (playerY >= position.y) {
            order.beforePlayer = false;
        } else if (playerY < position.y) {
            order.beforePlayer = true;
        }
    }
}
