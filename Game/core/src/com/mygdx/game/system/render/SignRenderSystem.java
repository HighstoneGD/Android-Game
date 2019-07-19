package com.mygdx.game.system.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameData;
import com.mygdx.game.controlling.BonusManager;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.util.interfaces.SpeedStateListener;

import java.util.ArrayList;
import java.util.List;

public class SignRenderSystem extends EntitySystem implements SpeedStateListener {

    private static final String TEXT = "Speed up!";

    private SpriteBatch batch;
    private Viewport viewport;
    private BitmapFont font;

    private float timer;
    private float stringWidth;
    private float oldFontScale;

    public SignRenderSystem(BasicGameScreen screen) {
        batch = screen.getBatch();
        viewport = screen.getViewport();
        font = screen.getAssetManager().get(AssetDescriptors.LARGE_FONT);

        font.getData().setScale(0.1f);
        calculateStringWidth();

        timer = 0;

        BonusManager.INSTANCE.subscribe(this);
    }

    private void calculateStringWidth() {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, TEXT);
        stringWidth = glyphLayout.width;
    }

    @Override
    public void onSpeedUp() {
        timer = GameData.SPEED_UP_TIME;
    }

    @Override
    public void update(float deltaTime) {
        decreaseTimer(deltaTime);
        if (timer != 0) {
            drawText();
        }
    }

    private void decreaseTimer(float delta) {
        if (timer != 0) {
            timer -= delta;

            if (timer < 0) {
                timer = 0;
            }
        }
    }

    private void drawText() {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        font.draw(batch, TEXT,
                GameData.WORLD_CENTER_X - stringWidth / 2f,
                GameData.WORLD_HEIGHT * 0.62f);

        batch.end();
    }
}
