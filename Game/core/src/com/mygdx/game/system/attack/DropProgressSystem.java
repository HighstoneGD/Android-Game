package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.PotComponent;

public class DropProgressSystem extends IteratingSystem {

    private static final Family POTS = Family.all(
            PotComponent.class,
            PositionComponent.class
    ).get();

    public DropProgressSystem() {
        super(POTS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);

        float aim = potComponent.aimY;
        float start = Constants.WORLD_HEIGHT + 10f;
        float current = position.y;
        float passedDistance = start - current;
        float totalDistance = start - aim;
        float progress = passedDistance / totalDistance;

        potComponent.progress = progress;
    }
}
