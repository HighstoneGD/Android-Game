package com.mygdx.game.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.common.objects.Directions;
import com.mygdx.game.controlling.AvoidedPotsManager;
import com.mygdx.game.controlling.CooldownsManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.screen.BasicGameScreen;
import com.mygdx.game.system.attack.AttackSystem;
import com.mygdx.game.system.attack.DamageClearSystem;
import com.mygdx.game.system.attack.DamageOnCellSystem;
import com.mygdx.game.system.attack.DropProgressSystem;
import com.mygdx.game.system.attack.ShadowSystem;
import com.mygdx.game.system.attack.TargetSystem;
import com.mygdx.game.system.attack.potsystems.DropPotsSystem;
import com.mygdx.game.system.control.PlayerMovementSystem;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.DebugRenderSystem;
import com.mygdx.game.system.debug.InfoSystem;
import com.mygdx.game.system.debug.PositionsCalculationSystem;
import com.mygdx.game.system.moving.BoundsSystem;
import com.mygdx.game.system.moving.PlayerPresenseSystem;
import com.mygdx.game.system.control.DesktopControlSystem;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.system.render.PlayerRenderSystem;
import com.mygdx.game.system.render.PotsAfterPlayerRenderSystem;
import com.mygdx.game.system.render.PotsOrderSystem;
import com.mygdx.game.system.render.ShadowRenderSystem;
import com.mygdx.game.system.render.SmashAfterPlayerRenderSystem;
import com.mygdx.game.system.render.SmashesOrderSystem;
import com.mygdx.game.util.NumberConverter;
import com.mygdx.game.system.control.SimpleDirectionGestureDetector;
import com.mygdx.game.system.moving.WorldWrapSystem;
import com.mygdx.game.system.render.BackgroundRenderSystem;
import com.mygdx.game.system.render.PotsBeforePlayerRenderSystem;
import com.mygdx.game.system.render.SmashBeforePlayerRenderSystem;
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
        factory = new EntityFactory(this);

        createNotUpdatedSystems();
        createMovingSystems();
        createRenderSystems();
        createAttackAndBonusSystems();
        createDebugSystems();

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            createAndroidControl();
        } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            createDesktopControl();
        }

        addEntities();
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
        engine.addSystem(new DropPotsSystem(this));
        engine.addSystem(new DropProgressSystem());
        engine.addSystem(new ShadowSystem());
    }

    private void createRenderSystems() {
        engine.addSystem(new PotsOrderSystem());
        engine.addSystem(new SmashesOrderSystem());

        engine.addSystem(new BackgroundRenderSystem(viewport, game.getBatch()));
        engine.addSystem(new GranRenderSystem(getBatch(), viewport, assetManager));
        engine.addSystem(new ShadowRenderSystem(renderer, viewport));

        engine.addSystem(new PotsBeforePlayerRenderSystem(this));
        engine.addSystem(new SmashBeforePlayerRenderSystem(this));

        engine.addSystem(new PlayerRenderSystem(getBatch(), viewport, assetManager));

        engine.addSystem(new PotsAfterPlayerRenderSystem(this));
        engine.addSystem(new SmashAfterPlayerRenderSystem(this));
    }

    private void createDebugSystems() {
        engine.addSystem(new DebugRenderSystem(viewport, renderer));
        engine.addSystem(new DebugCameraSystem(camera,
                Constants.WORLD_CENTER_X, Constants.WORLD_CENTER_Y));
//        engine.addSystem(new InfoSystem());
    }

    private void createMovingSystems() {
        engine.addSystem(new PlayerMovementSystem(engine));
        engine.addSystem(new WorldWrapSystem(this, engine));
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new PlayerPresenseSystem());
    }

    private void createNotUpdatedSystems() {
        engine.addSystem(new NumberConverter());
        engine.addSystem(new PositionsCalculationSystem(x, y));
        engine.addSystem(new CellsSpawnSystem(factory));
        engine.addSystem(new TargetSystem(this));
    }

    private void addEntities() {
        factory.addBackground();
        engine.getSystem(CellsSpawnSystem.class).spawnCells(x, y);
        factory.addPlayer();
        factory.addGran();
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

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return game.getBatch();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public PooledEngine getEngine() {
        return engine;
    }

    public EntityFactory getFactory() {
        return factory;
    }
}
