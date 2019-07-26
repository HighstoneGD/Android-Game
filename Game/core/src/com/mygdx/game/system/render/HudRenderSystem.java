package com.mygdx.game.system.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.GameData;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.screen.game.LevelsScreen;

public class HudRenderSystem extends EntitySystem {

    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final AssetManager assetManager;

    private final GlyphLayout layout = new GlyphLayout();

    private TextureAtlas hud;

    private boolean renderScore;

    public HudRenderSystem(BasicGameScreen screen, Viewport viewport) {
        this.assetManager = screen.getAssetManager();
        this.batch = screen.getBatch();
        this.viewport = viewport;
        this.font = assetManager.get(AssetDescriptors.FONT);
        hud = assetManager.get(AssetDescriptors.HUD);

        if (screen.getClass() == LevelsScreen.class) {
            renderScore = false;
        } else {
            renderScore = true;
        }
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw() {
        drawScore();
        drawHP();
    }

    private void drawScore() {
        if (renderScore) {
            String scoreString = "Scr: " + Math.round(GameManager.INSTANCE.getScore());
            layout.setText(font, scoreString);
            font.draw(batch, scoreString,
                    20,
                    GameData.HUD_HEIGHT - 5f);
        }
    }

    private void drawHP() {
        int lives = HealthManager.getLives();
        boolean armor = HealthManager.hasArmor();

        int livesToRender = lives;

        TextureRegion heartTexture = hud.findRegion(RegionNames.HEART_ACTIVE);


        for (int i = 0; i < 3; i++) {
            if (livesToRender == 0) {
                heartTexture = hud.findRegion(RegionNames.HEART_INACTIVE);
            }

            batch.draw(
                    heartTexture,
                    GameData.HEARTS_POSITIONS[i] - GameData.HEART_SIZE / 2f, 150f - GameData.HEART_SIZE / 2f,
                    GameData.HEART_SIZE, GameData.HEART_SIZE
            );

            livesToRender--;
        }


        if (armor) {
            batch.draw(
                    hud.findRegion(RegionNames.ARMOR_ACTIVE),
                    GameData.HEARTS_POSITIONS[3] - GameData.HEART_SIZE / 2f, 150f - GameData.HEART_SIZE / 2f,
                    GameData.HEART_SIZE, GameData.HEART_SIZE
            );
        } else {
            batch.draw(
                    hud.findRegion(RegionNames.ARMOR_INACTIVE),
                    GameData.HEARTS_POSITIONS[3] - GameData.HEART_SIZE / 2f, 150f - GameData.HEART_SIZE / 2f,
                    GameData.HEART_SIZE, GameData.HEART_SIZE
            );
        }
    }
}
