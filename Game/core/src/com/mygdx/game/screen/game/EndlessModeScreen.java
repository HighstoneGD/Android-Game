package com.mygdx.game.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.controlling.AvoidedPotsManager;
import com.mygdx.game.controlling.CooldownsManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.system.moving.SimpleDirectionGestureDetector;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.moving.BoundsSystem;
import com.mygdx.game.system.moving.PlayerSystem;
import com.mygdx.game.system.moving.WorldWrapSystem;
import com.mygdx.game.system.attack.AttackSystem;
import com.mygdx.game.system.attack.DamageClearSystem;
import com.mygdx.game.system.attack.DamageOnCellSystem;
import com.mygdx.game.system.attack.TargetSystem;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.DebugRenderSystem;
import com.mygdx.game.system.debug.PositionsCalculationSystem;
import com.mygdx.game.system.render.BackgroundRenderSystem;
import com.mygdx.game.util.GdxUtils;

public class EndlessModeScreen extends BasicGameScreen implements Screen {

    private static final Logger log = new Logger(EndlessModeScreen.class.getName(), Logger.DEBUG);

    private final AndroidGame game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private EntityFactory factory;

    private float potSpawnSpeed;
    private int x;
    private int y;

    public EndlessModeScreen(AndroidGame game) {
        super(5, 5);
        this.x = super.x;
        this.y = super.y;
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        potSpawnSpeed = Constants.DEFAULT_POT_SPAWN_SPEED;

        AvoidedPotsManager.reset();
        CooldownsManager.resetCooldown();
        HealthManager.reset();

        camera = new OrthographicCamera();
        viewport = new FillViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        engine = new PooledEngine();
        factory = new EntityFactory(engine, assetManager);

        createNotUpdatedSystems();

        createMovingSystems();

        createRenderSystems();

        createAttackAndBonusSystems();

        createDebugSystems();

        addEntities();

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            createAndroidControl();
        }
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
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
        renderer.dispose();
    }

    private void createAttackAndBonusSystems() {
        engine.addSystem(new DamageOnCellSystem());
        engine.addSystem(new DamageClearSystem());
        engine.addSystem(new AttackSystem(potSpawnSpeed, this, engine));
    }

    private void createRenderSystems() {
        engine.addSystem(new BackgroundRenderSystem(viewport, game.getBatch()));
    }

    private void createDebugSystems() {
        engine.addSystem(new DebugRenderSystem(viewport, renderer));
        engine.addSystem(new DebugCameraSystem(camera,
                Constants.WORLD_CENTER_X, Constants.WORLD_CENTER_Y));
    }

    private void createMovingSystems() {
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new WorldWrapSystem(this, engine));
        engine.addSystem(new BoundsSystem());
    }

    private void createNotUpdatedSystems() {
        engine.addSystem(new PositionsCalculationSystem(x, y));
        engine.addSystem(new CellsSpawnSystem(factory));
        engine.addSystem(new TargetSystem(this));
    }

    private void addEntities() {
        factory.addBackground();
        engine.getSystem(CellsSpawnSystem.class).spawnCells(x, y);
        factory.addPlayer();
    }

    private void createAndroidControl() {
        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            @Override
            public void onUp() {
                try {
                    engine.getSystem(PlayerSystem.class).moveUp(engine.getSystem(PlayerSystem.class).positionOnGrid);
                } catch (Exception e) {}
            }

            @Override
            public void onRight() {
                try {
                    engine.getSystem(PlayerSystem.class).moveRight(engine.getSystem(PlayerSystem.class).positionOnGrid);
                } catch (Exception e) {}
            }

            @Override
            public void onLeft() {
                try {
                    engine.getSystem(PlayerSystem.class).moveLeft(engine.getSystem(PlayerSystem.class).positionOnGrid);
                } catch (Exception e) {}
            }

            @Override
            public void onDown() {
                try {
                    engine.getSystem(PlayerSystem.class).moveDown(engine.getSystem(PlayerSystem.class).positionOnGrid);
                } catch (Exception e) {}
            }
        }));
    }
}
