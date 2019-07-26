package com.mygdx.game.screen.menu;

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

public class HighscoresScreen extends MenuScreenBase {

    public HighscoresScreen(AndroidGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureRegion background = uiSkin.getRegion(RegionNames.MENU_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(background));

        Label label = new Label("SCORES", uiSkin);

        TextButton lastHSButton = createLastHighscoresButton();
        TextButton topPlayersButton = createHighscoresTableButton();
        TextButton backButton = createBackButton();

        Table buttonTable = new Table();
        buttonTable.defaults().pad(20);

        buttonTable.add(lastHSButton).row();
        buttonTable.add(topPlayersButton).row();
        buttonTable.add(backButton);

        buttonTable.center();

        table.add(label).row();
        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private TextButton createLastHighscoresButton() {
        TextButton lastHSButton = new TextButton("TOP 5", uiSkin);
        lastHSButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                best();
            }
        });
        return lastHSButton;
    }

    private TextButton createHighscoresTableButton() {
        TextButton tableButton = new TextButton("WORLD", uiSkin);
        return tableButton;
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

    private void best() {
        game.setScreen(new FiveHighscoresScreen(game));
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
