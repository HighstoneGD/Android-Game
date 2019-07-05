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
import com.mygdx.game.controlling.scores.UserData;

public class FiveHighscoresScreen extends MenuScreenBase {

    private Skin uiSkin;

    public FiveHighscoresScreen(AndroidGame game) {
        super(game);
        uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureRegion background = uiSkin.getRegion(RegionNames.MENU_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(background));

        Label label = new Label("TOP 5", uiSkin);

        StringBuilder builder = new StringBuilder();
        UserData userData = game.getScoreManager().userData;

        if (userData.highscores != null) {
            for (int i = 0; i < userData.highscores.size() && i < 5; ++i) {
                builder.append("\n" + (i + 1) + ". " + userData.highscores.get(userData.highscores.size() - i - 1));
            }

            builder.append("\n");
        }

        Label hsLabel = new Label(builder.toString(), uiSkin);
        hsLabel.setStyle(uiSkin.get("small", Label.LabelStyle.class));

        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HighscoresScreen(game));
            }
        });

        table.add(label).row();
        table.add(hsLabel).row();
        table.add(backButton);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
}
