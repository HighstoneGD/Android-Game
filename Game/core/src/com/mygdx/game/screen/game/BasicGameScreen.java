package com.mygdx.game.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
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

    protected Stage stage;

    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected ShapeRenderer renderer;
    protected Viewport viewport;
    protected Viewport hudViewport;

    protected EntityFactory factory;
    protected PooledEngine engine;

    protected boolean isPaused;
    private boolean gameOver;

    protected float potSpawnSpeed;
    public int x;
    public int y;

    public BasicGameScreen(AndroidGame game, int x, int y) {
        this.x = x;
        this.y = y;
        this.game = game;
        batch = game.getBatch();
        assetManager = game.getAssetManager();
        gameOver = false;
    }

    @Override
    public void show() {
        isPaused = false;

        resetManagers();

        initAttributes();

        stage = new Stage(hudViewport, batch);

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
        if (!isPaused) {
            GdxUtils.clearScreen();
            engine.update(delta);

            if (HealthManager.getLives() == 0) {
                gameOver = true;
                gameOver();
            }
        }

        if (gameOver) {
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        stage.dispose();
    }

    protected void gameOver() {
        gameOverAction();
        Gdx.input.setInputProcessor(stage);
        isPaused = true;

        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        Dialog dialog = new Dialog("", uiSkin);
        Label label = new Label("YOU LOST", uiSkin);
        label.setStyle(uiSkin.get("small", Label.LabelStyle.class));
        dialog.defaults().pad(20f);
        dialog.text(label).getButtonTable().defaults().pad(20f);

        Button repeatButton = repeatButton(uiSkin);
        Button adButton = adButton(uiSkin);
        Button homeButton = homeButton(uiSkin);

        dialog.button(repeatButton);
        dialog.button(adButton);
        dialog.button(homeButton);
        dialog.center();
        dialog.show(stage);
    }

    protected abstract void gameOverAction();

    private void toPlayScreen() {
        game.setScreen(new PlayScreen(game));
    }

    private void watchAd() {}

    private void repeat() {
        if (this.getClass() == EndlessModeScreen.class) {
            game.setScreen(new EndlessModeScreen(game));
        } else {
            game.setScreen(new LevelsScreen(game));
        }

    }

    private Button repeatButton(Skin uiSkin) {
        Button repeatButton = new Button();
        repeatButton.setStyle(uiSkin.get("repeat", Button.ButtonStyle.class));
        repeatButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                repeat();
            }
        });
        return repeatButton;
    }

    private Button adButton(Skin uiSkin) {
        Button adButton = new Button();
        adButton.setStyle(uiSkin.get("ad", Button.ButtonStyle.class));
        adButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                watchAd();
            }
        });
        return adButton;
    }

    private Button homeButton(Skin uiSkin) {
        Button homeButton = new Button();
        homeButton.setStyle(uiSkin.get("home", Button.ButtonStyle.class));
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toPlayScreen();
            }
        });
        return homeButton;
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
