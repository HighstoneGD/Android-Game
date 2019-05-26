package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.ShadowComponent;
import com.mygdx.game.component.marking.PotComponent;

public class ShadowSystem extends IteratingSystem {

    private static final Family POTS = Family.all(
            ShadowComponent.class,
            PotComponent.class
    ).get();

    public ShadowSystem() {
        super(POTS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShadowComponent shadow = Mappers.SHADOW.get(entity);
        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);

        shadow.shadowHeight = Constants.MAX_SHADOW_SIZE * potComponent.progress;
    }
}
