package com.mygdx.game.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.controlling.AvoidedPotsManager;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.screen.menu.PlayScreen;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.util.render.GdxUtils;

public abstract class BasicGameScreen implements Screen {

    protected final AndroidGame game;
    protected final AssetManager assetManager;

    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected ShapeRenderer renderer;
    protected Viewport viewport;
    protected Viewport hudViewport;

    protected EntityFactory factory;
    protected PooledEngine engine;

    protected float potSpawnSpeed;
    public int x;
    public int y;

    public BasicGameScreen(AndroidGame game, int x, int y) {
        this.x = x;
        this.y = y;
        this.game = game;
        batch = game.getBatch();
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        resetManagers();

        initAttributes();

        initPotSpawnSpeed();

        initSystems();

        addEntities();
    }

    private void resetManagers() {
        AvoidedPotsManager.reset();
        resetHealthManager();
        GameManager.INSTANCE.resetScore();
        GameManager.INSTANCE.resetCooldown();
    }

    private void initAttributes() {
        camera = new OrthographicCamera();
        viewport = new FillViewport(GameData.WORLD_WIDTH, GameData.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameData.HUD_WIDTH, GameData.HUD_HEIGHT);
        renderer = new ShapeRenderer();

        engine = new PooledEngine();
        factory = new EntityFactory(this);
    }

    protected abstract void resetHealthManager();

    protected abstract void initPotSpawnSpeed();

    protected abstract void initSystems();

    public abstract void potThrown();

    private void addEntities() {
        factory.addBackground();
        engine.getSystem(CellsSpawnSystem.class).spawnCells(x, y);
        factory.addPlayer();
        factory.addGran();
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);

        if (HealthManager.getLives() == 0) {
            gameOver();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
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
        renderer.dispose();
    }

    protected void gameOver() {
        game.setScreen(new PlayScreen(game));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public EntityFactory getFactory() {
        return factory;
    }

    public PooledEngine getEngine() {
        return engine;
    }
}
