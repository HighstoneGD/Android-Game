package com.mygdx.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.GameData;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.screen.game.EndlessModeScreen;
import com.mygdx.game.screen.game.LevelsScreen;

public class PlayScreen extends MenuScreenBase {

    private TextureAtlas uiAtlas;
    private Skin uiSkin;

    public PlayScreen(AndroidGame game) {
        super(game);
        uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureRegion background = uiAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(background));

        Label label = new Label("PLAY", uiSkin);

        TextButton levelsButton = createLevelsButton();
        TextButton endlessModeButton = createEndlessModeButton();
        TextButton backButton = createBackButton();

        Table buttonTable = new Table();
        buttonTable.defaults().pad(20f);

        buttonTable.add(levelsButton).width(GameData.MENU_BUTTON_WIDTH).height(GameData.MENU_BUTTON_HEIGHT).row();
        buttonTable.add(endlessModeButton).width(GameData.MENU_BUTTON_WIDTH).height(GameData.MENU_BUTTON_HEIGHT).row();
        buttonTable.add(backButton).width(GameData.MENU_BUTTON_WIDTH).height(GameData.MENU_BUTTON_HEIGHT);

        buttonTable.center();

        table.add(label).row();
        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private TextButton createLevelsButton() {
        TextButton levelsButton = new TextButton("LEVEL - " + (GameManager.INSTANCE.getLevelsAccomplished() + 1), uiSkin);
        levelsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                levels();
            }
        });
        return levelsButton;
    }

    private TextButton createEndlessModeButton() {
        TextButton endlessModeButton;
        if (!GameManager.INSTANCE.endlessModeUnlocked()) {
            endlessModeButton = new TextButton("", uiSkin);
            endlessModeButton.setStyle(uiSkin.get("locked", TextButton.TextButtonStyle.class));
        } else {
            endlessModeButton = new TextButton("ENDLESS", uiSkin);
            endlessModeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    endlessMode();
                }
            });
        }

        return endlessModeButton;
    }

    private TextButton createBackButton() {
        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });
        return backButton;
    }

    private void levels() {
        game.setScreen(new LevelsScreen(game));
    }

    private void endlessMode() {
        game.setScreen(new EndlessModeScreen(game));
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
