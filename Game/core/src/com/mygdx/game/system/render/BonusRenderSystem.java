package com.mygdx.game.system.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.component.BonusComponent;
import com.mygdx.game.component.PositionComponent;
import com.mygdx.game.screen.game.BasicGameScreen;

public class BonusRenderSystem extends IteratingSystem {

    private static final Family CELLS = Family.all(
            PositionComponent.class,
            BonusComponent.class
    ).get();
    private SpriteBatch batch;
    private Viewport viewport;
    private AssetManager assetManager;

    public BonusRenderSystem(BasicGameScreen screen, Viewport viewport) {
        super(CELLS);
        this.batch = screen.getBatch();
        this.viewport = viewport;
        this.assetManager = screen.getAssetManager();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BonusComponent bonus = Mappers.BONUS.get(entity);

        if (bonus.timers.size() > 0) {
            drawBonusTexture(entity);
        }
    }

    private void drawBonusTexture(Entity entity) {
        PositionComponent position = Mappers.POSITION.get(entity);
        draw(position.x, position.y);
    }

    private void draw(float x, float y) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(
                assetManager.get(AssetDescriptors.STATIC).findRegion(RegionNames.BONUS),
                x - Constants.BONUS_WIDTH / 2f, y - Constants.BONUS_HEIGHT / 2f,
                Constants.BONUS_WIDTH, Constants.BONUS_HEIGHT
        );
        batch.end();
    }
}
