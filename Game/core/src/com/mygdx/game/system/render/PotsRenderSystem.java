package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AnimationComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.PotComponent;
import com.mygdx.game.screen.BasicGameScreen;

public class PotsRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PotComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();
    private SpriteBatch batch;

    public PotsRenderSystem(BasicGameScreen screen) {
        super(FAMILY);
        batch = screen.getBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        AnimationComponent animationComponent = Mappers.ANIMATION_COMPONENT.get(entity);

        animationComponent.elapsedTime += deltaTime;

        batch.begin();

        batch.draw(animationComponent.animation.getKeyFrame(animationComponent.elapsedTime, true),
                position.x - dimension.width / 2.1f, position.y - dimension.height / 2f,
                dimension.width, dimension.height
        );

        batch.end();
    }
}
