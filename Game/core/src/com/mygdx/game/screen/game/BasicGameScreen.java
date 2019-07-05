package com.mygdx.game.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.controlling.SoundsManager;
import com.mygdx.game.screen.menu.PlayScreen;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.system.movement.PlayerMovementSystem;
import com.mygdx.game.system.movement.SimpleDirectionGestureDetector;
import com.mygdx.game.system.render.BackgroundRenderSystem;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.system.render.HudRenderSystem;
import com.mygdx.game.system.render.PlayerRenderSystem;
import com.mygdx.game.system.render.PotsAfterPlayerRenderSystem;
import com.mygdx.game.system.render.PotsBeforePlayerRenderSystem;
import com.mygdx.game.system.render.ShadowRenderSystem;
import com.mygdx.game.system.render.SmashAfterPlayerRenderSystem;
import com.mygdx.game.system.render.SmashBeforePlayerRenderSystem;
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
    protected boolean gameWon;

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
        gameWon = false;
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

        getSoundsManager().playMusic();
    }

    private void resetManagers() {
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

        if (gameOver || gameWon) {
            GdxUtils.clearScreen(Color.SALMON);
            engine.getSystem(BackgroundRenderSystem.class).update(delta);
            engine.getSystem(GranRenderSystem.class).update(delta);
            engine.getSystem(PotsBeforePlayerRenderSystem.class).update(delta);
            engine.getSystem(SmashBeforePlayerRenderSystem.class).update(delta);
            engine.getSystem(ShadowRenderSystem.class).update(delta);
            engine.getSystem(PlayerRenderSystem.class).update(delta);
            engine.getSystem(PotsAfterPlayerRenderSystem.class).update(delta);
            engine.getSystem(SmashAfterPlayerRenderSystem.class).update(delta);
            engine.getSystem(HudRenderSystem.class).update(delta);
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
        getSoundsManager().stopMusic();
        renderer.dispose();
        stage.dispose();
    }

    private void gameOver() {
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

    private void watchAd() {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

                @Override
                public void onUp() {
                    try {
                        engine.getSystem(PlayerMovementSystem.class).movePlayer(Directions.UP);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onRight() {
                    try {
                        engine.getSystem(PlayerMovementSystem.class).movePlayer(Directions.RIGHT);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onLeft() {
                    try {
                        engine.getSystem(PlayerMovementSystem.class).movePlayer(Directions.LEFT);
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onDown() {
                    try {
                        engine.getSystem(PlayerMovementSystem.class).movePlayer(Directions.DOWN);
                    } catch (Exception e) {
                    }
                }
            }));
        }

        HealthManager.incrementLives();
        gameOver = false;
        isPaused = false;
    }

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

    public SoundsManager getSoundsManager() {
        return game.getSoundsManager();
    }
}
