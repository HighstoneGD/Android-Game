package com.mygdx.game.screen.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.GameData;
import com.mygdx.game.screen.menu.MenuScreen;
import com.mygdx.game.util.render.GdxUtils;

public class LoadingScreen implements Screen {

    private final AndroidGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private SpriteBatch batch;

    private float screenWidth;
    private float screenHeight;
    private float scale;
    private float oldScale = 0;

    private TextureAtlas textureAtlas;
    private BitmapFont font;
    private Animation<TextureRegion> animation;

    private String sign;
    private boolean changeScreen;
    private boolean animating = false;
    private boolean fontAdjusted = false;
    private float progress;
    private float waitTime = 0.75f;
    private float elapsedTime = 0;

    public LoadingScreen(AndroidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        viewport = new FillViewport(screenWidth, screenHeight);
        batch = game.getBatch();

        loadPreviewAssets();

        while (!assetManager.update()) {}

        initAnimationAndSound();
        loadAssets();
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();

        viewport.apply();
        batch.begin();

        draw();

        batch.end();

        finishAnimation();
        changeScreen();
    }

    private void update(float delta) {
        elapsedTime += delta;
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        if (!animating) {
            drawStatic();
        } else {
            drawAnimation();
        }
        drawFont();
    }

    private void drawStatic() {
        batch.draw(assetManager.get(AssetDescriptors.LOADING_BACKGROUND).findRegion(RegionNames.LOADING_BACKGROUND),
                0, 0,
                screenWidth, screenHeight
        );
    }

    private void drawAnimation() {
        batch.draw(
                animation.getKeyFrame(elapsedTime, false),
                0, 0,
                screenWidth, screenHeight
        );
    }

    private void finishAnimation() {
        if (animation.isAnimationFinished(elapsedTime)) {
            animating = false;
        }
    }

    private void changeScreen() {
        if (changeScreen && !animating) {
            font.getData().setScale(oldScale);
            game.setScreen(new MenuScreen(game));
        }
    }

    private void drawFont() {
        if (!fontAdjusted) {
            adjustFont();
        }

        font.draw(batch, sign + Math.round(progress * 100f) + "%",
                screenWidth * 0.25f, screenHeight * 0.28f);
    }

    private void adjustFont() {
        oldScale = font.getScaleX();
        scale = screenWidth / 500f;
        font.getData().setScale(scale);
        fontAdjusted = true;
    }

    private void initAnimationAndSound() {
        textureAtlas = assetManager.get(AssetDescriptors.GRAN_LOADING_ANIMATION);
        font = assetManager.get(AssetDescriptors.FONT);
        sign = "LOADING: ";
        animation = new Animation<>(GameData.FRAME_TIME * 3, textureAtlas.getRegions());
        animating = true;
        assetManager.get(AssetDescriptors.FUMAR_SOUND).play();
    }

    private void loadPreviewAssets() {
        assetManager.load(AssetDescriptors.FUMAR_SOUND);
        assetManager.load(AssetDescriptors.GRAN_LOADING_ANIMATION);
        assetManager.load(AssetDescriptors.LOADING_BACKGROUND);
        assetManager.load(AssetDescriptors.FONT);
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.LARGE_FONT);
        assetManager.load(AssetDescriptors.GAMEPLAY_BG);
        assetManager.load(AssetDescriptors.STATIC);
        assetManager.load(AssetDescriptors.HUD);
        assetManager.load(AssetDescriptors.UI_SKIN);
        loadAnimations();
        loadSounds();
    }

    private void loadAnimations() {
        loadPotAnimations();
        loadGranAnimations();
        loadPlayerAnimations();
        assetManager.load(AssetDescriptors.WIN_ANIMATION);
    }

    private void loadPotAnimations() {
        assetManager.load(AssetDescriptors.SIMPLE_TEXTURE);
        assetManager.load(AssetDescriptors.SIMPLE_SMASH);
        assetManager.load(AssetDescriptors.IRON_TEXTURE);
        assetManager.load(AssetDescriptors.IRON_SMASH);
        assetManager.load(AssetDescriptors.LARGE_TEXTURE);
        assetManager.load(AssetDescriptors.LARGE_SMASH);
        assetManager.load(AssetDescriptors.BONUS_TEXTURE);
        assetManager.load(AssetDescriptors.BONUS_SMASH);
        assetManager.load(AssetDescriptors.EXPLOSIVE_TEXTURE);
        assetManager.load(AssetDescriptors.EXPLOSIVE_SMASH);
    }

    private void loadGranAnimations() {
        assetManager.load(AssetDescriptors.GRAN_SIMPLE_THROW);
        assetManager.load(AssetDescriptors.GRAN_IRON_THROW);
        assetManager.load(AssetDescriptors.GRAN_LARGE_THROW);
        assetManager.load(AssetDescriptors.GRAN_EXPLOSIVE_THROW);
    }

    private void loadPlayerAnimations() {
        assetManager.load(AssetDescriptors.PLAYER_LEFT_JUMP);
        assetManager.load(AssetDescriptors.PLAYER_RIGHT_JUMP);
        assetManager.load(AssetDescriptors.PLAYER_VERTICAL_JUMP);
    }

    private void loadSounds() {
        assetManager.load(AssetDescriptors.CLAY_POT_SMASH_FIRST);
        assetManager.load(AssetDescriptors.CLAY_POT_SMASH_SECOND);
        assetManager.load(AssetDescriptors.EXPLOSION);
        assetManager.load(AssetDescriptors.IRON_POT_SMASH);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {}
}
