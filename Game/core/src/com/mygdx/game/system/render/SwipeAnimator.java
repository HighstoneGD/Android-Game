package com.mygdx.game.system.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameData;
import com.mygdx.game.screen.game.BasicGameScreen;

public class SwipeAnimator extends EntitySystem {

    private static final String TEXT = "Swipe to move";

    private Viewport viewport;
    private SpriteBatch batch;

    private Animation<TextureRegion> swipeAnimation;

    private float elapsedTime;

    public SwipeAnimator(BasicGameScreen screen) {
        batch = screen.getBatch();
        viewport = screen.getViewport();
        swipeAnimation = new Animation<>(GameData.FRAME_TIME, screen.getAssetManager().get(AssetDescriptors.SWIPE).getRegions());
        elapsedTime = 0;
    }

    @Override
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        draw();
    }

    private void draw() {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        drawAnimation();

        batch.end();
    }

    private void drawAnimation() {
        batch.draw(swipeAnimation.getKeyFrame(elapsedTime, true),
                0, GameData.WORLD_HEIGHT / 2f,
                40, 10);
    }
}
