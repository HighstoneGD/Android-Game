package com.mygdx.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameData;
import com.mygdx.game.util.services.GdxUtils;

public abstract class MenuScreenBase extends ScreenAdapter {

    protected final AndroidGame game;
    protected final AssetManager assetManager;

    protected Viewport viewport;
    protected Stage stage;

    protected Skin uiSkin;

    public MenuScreenBase(AndroidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
    }

    @Override
    public void show() {
        viewport = new FillViewport(GameData.HUD_WIDTH, GameData.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        stage.addActor(createUi());
    }

    protected abstract Actor createUi();

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
