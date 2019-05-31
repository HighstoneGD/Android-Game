package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.common.objects.PotType;
import com.mygdx.game.component.DimensionComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.component.marking.GranComponent;

public class GranRenderSystem extends IteratingSystem {

    private static final Family GRAN = Family.all(
            GranComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();
    private AssetManager assetManager;
    private SpriteBatch batch;
    private Viewport viewport;

    private Texture granStatic;
    private Animation<TextureRegion> simple;
    private Animation<TextureRegion> large;
    private Animation<TextureRegion> explosive;
    private Animation<TextureRegion> iron;
    private Animation<TextureRegion> bonus;

    public GranRenderSystem(SpriteBatch batch, Viewport viewport, AssetManager assetManager) {
        super(GRAN);
        this.batch = batch;
        this.viewport = viewport;
        this.assetManager = assetManager;
        granStatic = assetManager.get(AssetDescriptors.GRAN_STATIC);
        simple = new Animation<TextureRegion>(Constants.GRAN_FRAME_TIME, assetManager.get(AssetDescriptors.GRAN_SIMPLE_THROW).getRegions());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        GranComponent gran = Mappers.GRAN.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        if (!gran.isAnimating) {
            drawStatic(position, dimension);
        } else {
            gran.elapsedTime += deltaTime;

            if (gran.animatesType == PotType.SIMPLE) {
                drawSimple(gran, position, dimension);

                if (simple.isAnimationFinished(gran.elapsedTime)) {
                    gran.isAnimating = false;
                }

            }

        }
    }

    private void drawStatic(PositionComponent position, DimensionComponent dimension) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(
                granStatic,
                position.x - dimension.width / 2f, position.y,
                dimension.width, dimension.height
        );

        batch.end();
    }

    private void drawSimple(GranComponent gran, PositionComponent position, DimensionComponent dimension) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(
                simple.getKeyFrame(gran.elapsedTime, false),
                position.x - dimension.width / 2f, position.y,
                dimension.width, dimension.height
        );

        batch.end();
    }

    public void throwSimple() {
        ImmutableArray<Entity> gran = getEngine().getEntitiesFor(GRAN);
        for (Entity entity : gran) {
            GranComponent granComponent = Mappers.GRAN.get(entity);
            granComponent.isAnimating = true;
            granComponent.animatesType = PotType.SIMPLE;
            granComponent.elapsedTime = 0;
        }
    }
}
