package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AnimationComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.OrderComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.SmashComponent;
import com.mygdx.game.screen.game.BasicGameScreen;

public class SmashBeforePlayerRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            SmashComponent.class,
            PositionComponent.class,
            AnimationComponent.class,
            DimensionComponent.class
    ).get();
    private SpriteBatch batch;

    private static final Logger log = new Logger(SmashBeforePlayerRenderSystem.class.getName(), Logger.DEBUG);

    public SmashBeforePlayerRenderSystem(BasicGameScreen screen) {
        super(FAMILY);
        batch = screen.getBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        OrderComponent order = Mappers.ORDER.get(entity);

        if (order.beforePlayer) {
            PositionComponent position = Mappers.POSITION.get(entity);
            DimensionComponent dimension = Mappers.DIMENSION.get(entity);
            AnimationComponent animationComponent = Mappers.ANIMATION_COMPONENT.get(entity);
            animationComponent.elapsedTime += deltaTime;

            batch.begin();

            batch.draw(animationComponent.animation.getKeyFrame(animationComponent.elapsedTime, false),
                    position.x - dimension.width / 2f, position.y - dimension.height / 2f,
                    dimension.width, dimension.height
            );

            batch.end();

            if (animationComponent.animation.isAnimationFinished(animationComponent.elapsedTime)) {
                getEngine().removeEntity(entity);
            }
        }
    }
}
