package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.AnimationComponent;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.SmashComponent;
import com.mygdx.game.screen.BasicGameScreen;

public class SmashRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            SmashComponent.class,
            PositionComponent.class,
            AnimationComponent.class,
            DimensionComponent.class
    ).get();
    private SpriteBatch batch;
    private BasicGameScreen screen;

    private static final Logger log = new Logger(SmashRenderSystem.class.getName(), Logger.DEBUG);

    public SmashRenderSystem(BasicGameScreen screen) {
        super(FAMILY);
        this.screen = screen;
        batch = screen.getBatch();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
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
