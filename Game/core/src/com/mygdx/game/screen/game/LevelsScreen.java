package com.mygdx.game.screen.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.enums.Directions;
import com.mygdx.game.common.levels.Level;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.screen.menu.PlayScreen;
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
import com.mygdx.game.system.attack.DropPotsSystem;
import com.mygdx.game.system.movement.DesktopControlSystem;
import com.mygdx.game.system.movement.PlayerMovementSystem;
import com.mygdx.game.system.movement.PlayerPresenceSystem;
import com.mygdx.game.system.movement.SimpleDirectionGestureDetector;
import com.mygdx.game.system.movement.WorldWrapSystem;
import com.mygdx.game.system.debug.CellsSpawnSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.InfoSystem;
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

public class LevelsScreen extends BasicGameScreen {

    private static final Logger log = new Logger(LevelsScreen.class.getName(), Logger.DEBUG);

    Animation<TextureRegion> winAnimation;
    private float elapsedTime;
    private float timer;

    private Level level;
    private int potsLeft;
    private int lives;
    private boolean isAnimating;

    public LevelsScreen(AndroidGame game) {
        super(
                game,
                new Level(GameManager.INSTANCE.getLevelsAccomplished() + 1).getX(),
                new Level(GameManager.INSTANCE.getLevelsAccomplished() + 1).getY()
        );

        level = new Level(GameManager.INSTANCE.getLevelsAccomplished() + 1);
        potsLeft = level.getPotsAmount();
        lives = level.getLives();
        isAnimating = false;

        winAnimation = new Animation<TextureRegion>(
                0.1f, assetManager.get(AssetDescriptors.WIN_ANIMATION).getRegions()
        );
        elapsedTime = 0f;
        timer = 2f;
    }

    @Override
    protected void resetHealthManager() {
        HealthManager.reset(lives);
    }

    @Override
    protected void initPotSpawnSpeed() {
        potSpawnSpeed = level.getPotSpawnSpeed();
    }

    @Override
    protected void initSystems() {
        createNotUpdatedSystems();
        createMovementSystems();
        createAttackAndBonusSystems();
        createDebugSystems();
        createRenderSystems();
    }

    @Override
    public void potThrown() {
        potsLeft--;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (isAnimating) {
            viewport.apply();
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();

            drawAnimation(delta);

            batch.end();

            checkAnimation(delta);

        } else if (potsLeft == 0) {
            gameWon = true;
            render(delta);
            win();
        }
    }

    private void drawAnimation(float delta) {
        batch.draw(
                winAnimation.getKeyFrame(elapsedTime, false),
                0, 0,
                GameData.WORLD_WIDTH, GameData.WORLD_HEIGHT
        );
        elapsedTime += delta;
    }

    private void checkAnimation(float delta) {
        if (winAnimation.isAnimationFinished(elapsedTime)) {
            timer -= delta;

            if (timer <= 0) {
                isAnimating = false;
                super.render(delta);
                handlePostWinActions();
            }
        }
    }

    @Override
    protected void gameOverAction() {

    }

    private void win() {
        GameManager.INSTANCE.levelComplete();
        isPaused = true;
        isAnimating = true;
    }

    private void handlePostWinActions() {
        Gdx.input.setInputProcessor(stage);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        Dialog dialog = new Dialog("", uiSkin);
        Label label = new Label("LEVEL COMPLETE!", uiSkin);
        label.setStyle(uiSkin.get("small", Label.LabelStyle.class));
        dialog.defaults().pad(20f);
        dialog.text(label).getButtonTable().defaults().pad(20f);

        Button homeButton = homeButton(uiSkin);
        Button nextButton = nextButton(uiSkin);

        dialog.button(nextButton);
        dialog.button(homeButton);
        dialog.center();
        dialog.show(stage);
    }

    private Button homeButton(Skin uiSkin) {
        final Button homeButton = new Button();
        homeButton.setStyle(uiSkin.get("home", Button.ButtonStyle.class));
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                home();
            }
        });
        return homeButton;
    }

    private Button nextButton(Skin uiSkin) {
        Button nextButton = new Button();
        nextButton.setStyle(uiSkin.get("next", Button.ButtonStyle.class));
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                next();
            }
        });
        return nextButton;
    }

    private void home() {
        game.setScreen(new PlayScreen(game));
    }

    private void next() {
        game.setScreen(new LevelsScreen(game));
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

    private void createAttackAndBonusSystems() {
        engine.addSystem(new TargetSystem(this));
        engine.addSystem(new DamageOnCellSystem());
        engine.addSystem(new DamageClearSystem());
        engine.addSystem(new AttackSystem(potSpawnSpeed, this, level.getPotTypes()));
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
        engine.addSystem(new InfoSystem(this));
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

    public int getPotsLeft() {
        return potsLeft;
    }
}
