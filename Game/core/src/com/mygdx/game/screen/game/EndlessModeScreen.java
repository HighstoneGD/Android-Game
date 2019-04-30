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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.SimpleDirectionGestureDetector;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.BoundsSystem;
import com.mygdx.game.system.bonuses.BonusSystem;
import com.mygdx.game.system.debug.GridRenderSystem;
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

    private final boolean DEBUG = false;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private EntityFactory factory;

    private float potSpawnSpeed;
    private float bonusSpawnSpeed;
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
        log.debug("show()");

        potSpawnSpeed = Constants.DEFAULT_POT_SPAWN_SPEED;
        bonusSpawnSpeed = Constants.DEFAULT_BONUS_SPAWN_SPEED;
        camera = new OrthographicCamera();
        viewport = new FillViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        engine = new PooledEngine();
        factory = new EntityFactory(engine, assetManager);

        engine.addSystem(new PositionsCalculationSystem(x, y));
        engine.addSystem(new CellsSpawnSystem(factory));
        engine.addSystem(new BackgroundRenderSystem(viewport, game.getBatch()));
        engine.addSystem(new DebugRenderSystem(viewport, renderer));
        engine.addSystem(new DebugCameraSystem(camera,
                Constants.WORLD_CENTER_X, Constants.WORLD_CENTER_Y));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new WorldWrapSystem(this, engine));
        engine.addSystem(new BoundsSystem());

        engine.addSystem(new DamageOnCellSystem());
        engine.addSystem(new DamageClearSystem());
        engine.addSystem(new AttackSystem(potSpawnSpeed, this, engine));
        engine.addSystem(new TargetSystem(this));
        engine.addSystem(new BonusSystem(bonusSpawnSpeed, this));
//        engine.addSystem(new GridRenderSystem(viewport, renderer));

        addEntities();

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            createAndroidControl();
        }
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
}
