package com.mygdx.game.screen;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.common.EntityFactory;

public abstract class BasicGameScreen {
    public int x;
    public int y;
    private SpriteBatch batch;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private EntityFactory factory;

    public BasicGameScreen(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract AssetManager getAssetManager();

    public abstract SpriteBatch getBatch();

    public abstract OrthographicCamera getCamera();

    public abstract PooledEngine getEngine();

    public abstract EntityFactory getFactory();
}
