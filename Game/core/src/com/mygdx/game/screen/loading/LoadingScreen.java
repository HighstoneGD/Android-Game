package com.mygdx.game.screen.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mygdx.game.common.Constants;
import com.mygdx.game.screen.game.EndlessModeScreen;
import com.mygdx.game.util.GdxUtils;

public class LoadingScreen implements Screen {

    private OrthographicCamera camera;
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
    private int frames;
    private float time;
    private String sign;


    public LoadingScreen(AndroidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        batch = game.getBatch();

//        assetManager.load(AssetDescriptors.FUMAR_SOUND);
        assetManager.load(AssetDescriptors.GRAN_LOADING_ANIMATION);
        assetManager.load(AssetDescriptors.LOADING_BACKGROUND);
        assetManager.load(AssetDescriptors.FONT);

        while (!assetManager.update()) {

        }

        assetManager.load(AssetDescriptors.GAMEPLAY_BG);
        assetManager.load(AssetDescriptors.SIMPLE_TEXTURE);
        assetManager.load(AssetDescriptors.UI_SKIN);

        textureAtlas = assetManager.get(AssetDescriptors.GRAN_LOADING_ANIMATION);
        font = assetManager.get(AssetDescriptors.FONT);
        sign = "LOADING...";
        frames = 18;
        time = 2.5f;
        animation = new Animation<TextureRegion>(time/frames, textureAtlas.getRegions());
        animating = true;
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        batch.begin();

        draw();
        elapsedTime += delta;
        batch.draw(
                animation.getKeyFrame(elapsedTime, false),
                0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        );

        font.draw(batch, sign, Gdx.graphics.getWidth() * 0.37f, Gdx.graphics.getHeight() * 0.28f);

        batch.end();

        if (animation.isAnimationFinished(elapsedTime)) {
            animating = false;

            try {
                Thread.sleep(300);
            } catch (Exception e) {}
        }

        if(changeScreen && !animating) {
            game.setScreen(new EndlessModeScreen(game));
        }
    }

    private void update(float delta) {
        progress = assetManager.getProgress();

        if(assetManager.update()) {
            waitTime -= delta;

            if(waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        batch.draw(assetManager.get(AssetDescriptors.LOADING_BACKGROUND).findRegion(RegionNames.LOADING_BACKGROUND),
                0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        );
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
