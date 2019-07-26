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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.controlling.BonusManager;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.controlling.SoundsManager;
import com.mygdx.game.system.attack.BonusClearSystem;
import com.mygdx.game.system.attack.BonusPickSystem;
import com.mygdx.game.system.attack.DamageClearSystem;
import com.mygdx.game.system.attack.DamageOnCellSystem;
import com.mygdx.game.system.attack.DropPotsSystem;
import com.mygdx.game.system.attack.DropProgressSystem;
import com.mygdx.game.system.attack.ShadowSystem;
import com.mygdx.game.system.attack.TargetSystem;
import com.mygdx.game.system.attack.bonus.ArmorBonusSystem;
import com.mygdx.game.system.attack.bonus.LifeBonusSystem;
import com.mygdx.game.system.attack.bonus.SpeedUpBonusSystem;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.system.debug.PositionsCalculationSystem;
import com.mygdx.game.system.movement.DesktopControlSystem;
import com.mygdx.game.system.movement.PlayerMovementSystem;
import com.mygdx.game.system.movement.PlayerPresenceSystem;
import com.mygdx.game.system.movement.SimpleDirectionGestureDetector;
import com.mygdx.game.system.movement.WorldWrapSystem;
import com.mygdx.game.system.render.BackgroundRenderSystem;
import com.mygdx.game.system.render.BonusRenderSystem;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.system.render.HudRenderSystem;
import com.mygdx.game.system.render.PlayerRenderSystem;
import com.mygdx.game.system.render.PotsAfterPlayerRenderSystem;
import com.mygdx.game.system.render.PotsBeforePlayerRenderSystem;
import com.mygdx.game.system.render.PotsOrderSystem;
import com.mygdx.game.system.render.ShadowRenderSystem;
import com.mygdx.game.system.render.SignRenderSystem;
import com.mygdx.game.system.render.SmashAfterPlayerRenderSystem;
import com.mygdx.game.system.render.SmashBeforePlayerRenderSystem;
import com.mygdx.game.system.render.SmashesOrderSystem;
import com.mygdx.game.util.services.DialogConstructor;
import com.mygdx.game.util.services.NumberConverter;
import com.mygdx.game.util.services.GdxUtils;

public abstract class BasicGameScreen implements Screen {

    protected final AndroidGame game;
    protected final AssetManager assetManager;

    protected Stage stage;
    protected Dialog gameOverDialog;

    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected ShapeRenderer renderer;
    protected Viewport viewport;
    protected Viewport hudViewport;

    protected EntityFactory factory;
    protected PooledEngine engine;

    protected boolean isPaused;
    protected boolean gameOver;
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
        gameOverDialog = DialogConstructor.createGameOverDialog(game, this);
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
        BonusManager.INSTANCE.resetSpeed();
    }

    private void initAttributes() {
        camera = new OrthographicCamera();
        viewport = new FillViewport(GameData.WORLD_WIDTH, GameData.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameData.HUD_WIDTH, GameData.HUD_HEIGHT);
        renderer = new ShapeRenderer();

        engine = new PooledEngine();
        factory = new EntityFactory(this);
    }

    protected void initSystems() {
        createNotUpdatedSystems();
        createMovementSystems();
        createAttackAndBonusSystems();
        createRenderSystems();
    }

    private void createNotUpdatedSystems() {
        engine.addSystem(new NumberConverter());
        engine.addSystem(new PositionsCalculationSystem(x, y));
        engine.addSystem(new CellsSpawnSystem(factory));
    }

    private void createMovementSystems() {
        engine.addSystem(new PlayerMovementSystem(engine));
        engine.addSystem(new WorldWrapSystem(this, engine));
        engine.addSystem(new PlayerPresenceSystem());

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            createAndroidControl();
        } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            createDesktopControl();
        }
    }

    protected void createAttackAndBonusSystems() {
        engine.addSystem(new TargetSystem(this));
        engine.addSystem(new DamageOnCellSystem());
        engine.addSystem(new DamageClearSystem());
        engine.addSystem(new DropPotsSystem(this));
        engine.addSystem(new DropProgressSystem());
        engine.addSystem(new ShadowSystem());
        engine.addSystem(new BonusClearSystem());
        engine.addSystem(new BonusPickSystem());
        engine.addSystem(new ArmorBonusSystem());
        engine.addSystem(new LifeBonusSystem());
        engine.addSystem(new SpeedUpBonusSystem());
    }

    private void createRenderSystems() {
        engine.addSystem(new PotsOrderSystem());
        engine.addSystem(new SmashesOrderSystem());

        engine.addSystem(new BackgroundRenderSystem(viewport, game.getBatch()));
        engine.addSystem(new GranRenderSystem(getBatch(), viewport, assetManager));
        engine.addSystem(new ShadowRenderSystem(renderer, viewport));
        engine.addSystem(new BonusRenderSystem(this, viewport));

        engine.addSystem(new PotsBeforePlayerRenderSystem(this));
        engine.addSystem(new SmashBeforePlayerRenderSystem(this));

        engine.addSystem(new PlayerRenderSystem(getBatch(), viewport, assetManager));

        engine.addSystem(new PotsAfterPlayerRenderSystem(this));
        engine.addSystem(new SmashAfterPlayerRenderSystem(this));

        engine.addSystem(new HudRenderSystem(this, hudViewport));
        engine.addSystem(new SignRenderSystem(this));
    }

    private void createAndroidControl() {
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

    private void createDesktopControl() {
        engine.addSystem(new DesktopControlSystem());
    }

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

    public void watchAd() {
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
        getSoundsManager().playMusic();
        gameOver = false;
        isPaused = false;
    }

    private void gameOver() {
        gameOverAction();
        getSoundsManager().playGameOverSound();
        getSoundsManager().stopMusic();
        Gdx.input.setInputProcessor(stage);
        isPaused = true;
        gameOverDialog.show(stage);
    }

    protected abstract void resetHealthManager();

    protected abstract void initPotSpawnSpeed();

    public abstract void potThrown();

    protected abstract void gameOverAction();

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

    public Viewport getViewport() {
        return viewport;
    }
}
