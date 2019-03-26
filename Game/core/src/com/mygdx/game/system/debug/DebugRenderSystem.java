package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.BoundsComponent;
import com.mygdx.game.debug.GameConfig;

public class DebugRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(BoundsComponent.class).get();

    private static final Logger log = new Logger(DebugRenderSystem.class.getName(), Logger.DEBUG);

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public DebugRenderSystem(Viewport viewport, ShapeRenderer renderer) {
        super(FAMILY);
        this.viewport = viewport;
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        Color oldColor = renderer.getColor().cpy();

        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GREEN);

        super.update(deltaTime);

        renderer.end();
        renderer.setColor(oldColor);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bc = Mappers.BOUNDS.get(entity);
        float halfWidth = bc.bounds.width / 2f;
        float halfHeight = bc.bounds.height / 2f;

        renderer.ellipse(bc.bounds.x - halfWidth, bc.bounds.y - halfHeight, bc.bounds.width, bc.bounds.height);
    }
}
