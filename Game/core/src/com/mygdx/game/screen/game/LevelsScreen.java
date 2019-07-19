package com.mygdx.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameData;
import com.mygdx.game.common.levels.Level;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.controlling.HealthManager;
import com.mygdx.game.system.attack.AttackSystem;
import com.mygdx.game.util.services.DialogConstructor;

public class LevelsScreen extends BasicGameScreen {

    private static final Logger log = new Logger(LevelsScreen.class.getName(), Logger.DEBUG);

    private Animation<TextureRegion> winAnimation;
    private Dialog lvlCompleteDialog;
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

        winAnimation = new Animation<>(
                0.1f, assetManager.get(AssetDescriptors.WIN_ANIMATION).getRegions()
        );
        elapsedTime = 0f;
        timer = 2f;
        lvlCompleteDialog = DialogConstructor.createLvlCompleteDialog(game);
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
    protected void createAttackAndBonusSystems() {
        engine.addSystem(new AttackSystem(potSpawnSpeed, this, level.getPotTypes()));
        super.createAttackAndBonusSystems();
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
        } else if (potsLeft == 0 && !gameOver) {
            if (!gameWon) {
                handleWinActions();
            }

            super.render(delta);
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

    private void handleWinActions() {
        gameWon = true;
        isPaused = true;
        isAnimating = true;
    }

    private void handlePostWinActions() {
        Gdx.input.setInputProcessor(stage);
        lvlCompleteDialog.show(stage);
    }

    @Override
    protected void gameOverAction() {

    }

    @Override
    public void potThrown() {
        potsLeft--;
    }

    public int getPotsLeft() {
        return potsLeft;
    }
}
