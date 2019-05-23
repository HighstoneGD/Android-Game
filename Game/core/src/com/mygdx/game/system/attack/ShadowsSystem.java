package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.ShadowComponent;

public class ShadowsSystem extends IteratingSystem {

    private static final Family SHADOWS = Family.all(
            PositionComponent.class,
            ShadowComponent.class
    ).get();
    private ShapeRenderer renderer;
    private Viewport viewport;

    private static final Logger log = new Logger(ShadowsSystem.class.getName(), Logger.DEBUG);

    public ShadowsSystem(ShapeRenderer renderer, Viewport viewport) {
        super(SHADOWS);
        this.renderer = renderer;
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShadowComponent shadowComponent = Mappers.SHADOW_COMPONENT.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);

        shadowComponent.time += deltaTime;

        if (shadowComponent.time >= shadowComponent.aimTime) {
            getEngine().removeEntity(entity);
            return;
        }

        float width = Constants.MAX_SHADOW_SIZE * 2f * shadowComponent.time / shadowComponent.aimTime;
        float height = Constants.MAX_SHADOW_SIZE * shadowComponent.time / shadowComponent.aimTime;
        float x = position.x - width / 2f;
        float y = position.y - height / 2f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(1, 1, 1, 1));

        renderer.ellipse(x, y, width, height);

        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
