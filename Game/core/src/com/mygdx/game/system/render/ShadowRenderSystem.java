package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.ShadowComponent;
import com.mygdx.game.component.marking.PotComponent;

public class ShadowRenderSystem extends IteratingSystem {

    private static final Family POTS = Family.all(
            ShadowComponent.class
    ).get();
    private ShapeRenderer renderer;
    private Viewport viewport;

    public ShadowRenderSystem(ShapeRenderer renderer, Viewport viewport) {
        super(POTS);
        this.renderer = renderer;
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShadowComponent shadow = Mappers.SHADOW.get(entity);
        PotComponent potComponent = Mappers.POT_COMPONENT.get(entity);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        draw(shadow, potComponent);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void draw(ShadowComponent shadow, PotComponent potComponent) {
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        viewport.apply();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        Color oldColor = renderer.getColor();
        renderer.setColor(0, 0, 0, 0.3f);
        float width = shadow.shadowHeight * 2f;
        float height = shadow.shadowHeight;
        renderer.ellipse(
                potComponent.aimX - width / 2f, potComponent.aimY - height / 2f,
                width, height
                );
        renderer.setColor(oldColor);
        renderer.end();
    }
}
