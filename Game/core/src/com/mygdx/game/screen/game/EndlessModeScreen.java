package com.mygdx.game.screen.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.screen.menu.PlayScreen;
import com.mygdx.game.system.ScoreInTimeSystem;
import com.mygdx.game.system.attack.AttackSystem;
import com.mygdx.game.system.attack.BonusClearSystem;
import com.mygdx.game.system.attack.BonusPickSystem;
import com.mygdx.game.system.attack.DamageClearSystem;
import com.mygdx.game.system.attack.DamageOnCellSystem;
import com.mygdx.game.system.attack.DropProgressSystem;
import com.mygdx.game.system.attack.ShadowSystem;
import com.mygdx.game.system.attack.TargetSystem;
import com.mygdx.game.system.attack.bonus.ArmorBonusSystem;
import com.mygdx.game.system.attack.bonus.LifeBonusSystem;
import com.mygdx.game.system.attack.bonus.SpeedUpBonusSystem;
import com.mygdx.game.system.attack.potsystems.DropPotsSystem;
import com.mygdx.game.system.movement.DesktopControlSystem;
import com.mygdx.game.system.movement.PlayerMovementSystem;
import com.mygdx.game.system.movement.PlayerPresenceSystem;
import com.mygdx.game.system.movement.SimpleDirectionGestureDetector;
import com.mygdx.game.system.movement.WorldWrapSystem;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.PositionsCalculationSystem;
import com.mygdx.game.system.render.BackgroundRenderSystem;
import com.mygdx.game.system.render.BonusRenderSystem;
import com.mygdx.game.system.render.GranRenderSystem;
import com.mygdx.game.system.render.HudRenderSystem;
import com.mygdx.game.system.render.PlayerRenderSystem;
import com.mygdx.game.system.render.PotsAfterPlayerRenderSystem;
import com.mygdx.game.system.render.PotsBeforePlayerRenderSystem;
import com.mygdx.game.system.render.PotsOrderSystem;
import com.mygdx.game.system.render.ShadowRenderSystem;
import com.mygdx.game.system.render.SmashAfterPlayerRenderSystem;
import com.mygdx.game.system.render.SmashBeforePlayerRenderSystem;
import com.mygdx.game.system.render.SmashesOrderSystem;
import com.mygdx.game.util.logic.NumberConverter;

import java.util.ArrayList;
import java.util.List;

public class EndlessModeScreen extends BasicGameScreen {

    private static final Logger log = new Logger(EndlessModeScreen.class.getName(), Logger.DEBUG);

    private List<PotType> potTypes;

    public EndlessModeScreen(AndroidGame game) {
        super(game, 5, 5);
        initPotTypes();
    }

    @Override
    protected void resetHealthManager() {
        HealthManager.reset();
    }

    @Override
    protected void initPotSpawnSpeed() {
        potSpawnSpeed = GameData.DEFAULT_POT_SPAWN_SPEED;
    }

    @Override
    protected void initSystems() {
        createNotUpdatedSystems();
        createMovementSystems();
        createAttackAndBonusSystems();
        createDebugSystems();
        createScoreSystem();
        createRenderSystems();
    }

    @Override
    public void potThrown() {

    }

    @Override
    protected void gameOverAction() {
        game.getScoreManager().addScore((long) GameManager.INSTANCE.getScore());
    }

    private void initPotTypes() {
        potTypes = new ArrayList<>();
        potTypes.add(PotType.SIMPLE);
        potTypes.add(PotType.LARGE);
        potTypes.add(PotType.BONUS);
        potTypes.add(PotType.EXPLOSIVE);
        potTypes.add(PotType.IRON);
    }

    private void createAttackAndBonusSystems() {
        engine.addSystem(new TargetSystem(this));
        engine.addSystem(new DamageOnCellSystem());
        engine.addSystem(new DamageClearSystem());
        engine.addSystem(new AttackSystem(potSpawnSpeed, this, potTypes));
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
    }

    private void createDebugSystems() {
        engine.addSystem(new DebugCameraSystem(camera,
                GameData.WORLD_CENTER_X, GameData.WORLD_CENTER_Y));
//        engine.addSystem(new InfoSystem());
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

    private void createNotUpdatedSystems() {
        engine.addSystem(new NumberConverter());
        engine.addSystem(new PositionsCalculationSystem(x, y));
        engine.addSystem(new CellsSpawnSystem(factory));
    }

    private void createScoreSystem() {
        engine.addSystem(new ScoreInTimeSystem());
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
}
