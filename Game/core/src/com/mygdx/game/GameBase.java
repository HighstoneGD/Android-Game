package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.controlling.ScoreManager;

public abstract class GameBase extends Game {

    private final AdController adController;

    private ScoreManager scoreManager;

    private SpriteBatch batch;
    private AssetManager assetManager;

    public GameBase(AdController adController) {
        this.adController = adController;
        scoreManager = new ScoreManager();
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();
        postCreate();
    }

    public abstract void postCreate();

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public AdController getAdController() {
        return adController;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }
}
