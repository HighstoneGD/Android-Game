package com.mygdx.game.screen.loading;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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

    private Viewport viewport;
    private SpriteBatch batch;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final AndroidGame game;
    private final AssetManager assetManager;

    private boolean animating = false;
    private TextureAtlas textureAtlas;
    private BitmapFont font;
    private Animation<TextureRegion> animation;
    private float elapsedTime = 0;
    private float animationBreakTime = 0;
    private String sign;


    public LoadingScreen(AndroidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FillViewport(GameData.HUD_WIDTH, GameData.HUD_HEIGHT);
        batch = game.getBatch();

        loadPreviewAssets();

        while (!assetManager.update()) {}

        loadAssets();

        textureAtlas = assetManager.get(AssetDescriptors.GRAN_LOADING_ANIMATION);
        font = assetManager.get(AssetDescriptors.FONT);
        sign = "LOADING: ";
        animation = new Animation<TextureRegion>(GameData.FRAME_TIME, textureAtlas.getRegions());
        animating = true;
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

    private void finishAnimation() {
        if (animation.isAnimationFinished(elapsedTime)) {
            animating = false;
            startTimer();
            startAnimation();
        }
    }

    private void changeScreen() {
        if(changeScreen && !animating) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void update(float delta) {
        updateTimers(delta);
        progress = assetManager.getProgress();

        if(assetManager.update()) {
            waitTime -= delta;

            if(waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void drawStatic() {
        batch.draw(assetManager.get(AssetDescriptors.LOADING_BACKGROUND).findRegion(RegionNames.LOADING_BACKGROUND),
                0, 0,
                GameData.HUD_WIDTH, GameData.HUD_HEIGHT
        );
    }

    private void drawAnimation() {
        batch.draw(
                animation.getKeyFrame(elapsedTime, false),
                0, 0,
                GameData.HUD_WIDTH, GameData.HUD_HEIGHT
        );
    }

    private void drawFont() {
        font.draw(batch, sign + Math.round(progress * 100f) + "%",
                GameData.HUD_WIDTH * 0.25f, GameData.HUD_HEIGHT * 0.28f);
    }

    private void draw() {
        if (!animating) {
            drawStatic();
        } else {
            drawAnimation();
        }
        drawFont();
    }

    private void updateTimers(float delta) {
        elapsedTime += delta;

        if (animationBreakTime > 0) {
            animationBreakTime += delta;
        }
    }

    private void loadPreviewAssets() {
//        assetManager.load(AssetDescriptors.FUMAR_SOUND);
        assetManager.load(AssetDescriptors.GRAN_LOADING_ANIMATION);
        assetManager.load(AssetDescriptors.LOADING_BACKGROUND);
        assetManager.load(AssetDescriptors.FONT);
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.LARGE_FONT);
        assetManager.load(AssetDescriptors.GAMEPLAY_BG);
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
        assetManager.load(AssetDescriptors.GRAN_SIMPLE_THROW);
        assetManager.load(AssetDescriptors.GRAN_IRON_THROW);
        assetManager.load(AssetDescriptors.GRAN_LARGE_THROW);
        assetManager.load(AssetDescriptors.GRAN_EXPLOSIVE_THROW);
        assetManager.load(AssetDescriptors.PLAYER_LEFT_JUMP);
        assetManager.load(AssetDescriptors.PLAYER_RIGHT_JUMP);
        assetManager.load(AssetDescriptors.PLAYER_VERTICAL_JUMP);
        assetManager.load(AssetDescriptors.STATIC);
        assetManager.load(AssetDescriptors.HUD);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.FILL_BAR);
    }

    private void startTimer() {
        if (animationBreakTime <= 0) {
            animationBreakTime = 5f;
        }
    }

    private void startAnimation() {
        if (animationBreakTime <= 0) {
            animating = true;
            elapsedTime = 0;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
