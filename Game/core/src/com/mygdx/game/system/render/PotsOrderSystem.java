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
import com.mygdx.game.component.marking.PotComponent;
import com.mygdx.game.util.NumberConverter;

public class PotsOrderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            OrderComponent.class,
            PotComponent.class,
            PositionComponent.class
    ).get();

    private static final Family PLAYER = Family.all(
            PlayerComponent.class,
            PositionOnGridComponent.class
    ).get();

    public PotsOrderSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ImmutableArray<Entity> player = getEngine().getEntitiesFor(PLAYER);
        PositionOnGridComponent positionOnGrid = Mappers.POSITION_ON_GRID.get(player.first());
        float playerY = getEngine().getSystem(NumberConverter.class).getCoordinates(positionOnGrid.xNumber, positionOnGrid.yNumber).y;

        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);
        OrderComponent order = Mappers.ORDER.get(entity);

        if (playerY >= potComponent.aimY) {
            order.beforePlayer = false;
        } else if (playerY < potComponent.aimY) {
            order.beforePlayer = true;
        }
    }
}
