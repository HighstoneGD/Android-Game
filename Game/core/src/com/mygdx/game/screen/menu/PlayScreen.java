package com.mygdx.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
        TextButton highscoresButton = createHighscoresButton();
        TextButton backButton = createBackButton();

        Table buttonTable = new Table();
        buttonTable.defaults().pad(20f);

        buttonTable.add(levelsButton).row();
        buttonTable.add(endlessModeButton).row();
        buttonTable.add(highscoresButton).row();
        buttonTable.add(backButton);

        buttonTable.center();

        table.add(label).row();
        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private TextButton createLevelsButton() {
        int level = GameManager.INSTANCE.getLevelsAccomplished() + 1;

        if (level > 8) {
            level = 8;
        }

        TextButton levelsButton = new TextButton("LEVEL " + level, uiSkin);
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
        String endlessModeButtonText= "";
        boolean endlessModeUnlocked = GameManager.INSTANCE.endlessModeUnlocked();

        if (endlessModeUnlocked) {
            endlessModeButtonText = "ENDLESS";
        }

        endlessModeButton = new TextButton(endlessModeButtonText, uiSkin);
        addButtonListener(endlessModeUnlocked, endlessModeButton);

        return endlessModeButton;
    }

    private TextButton createHighscoresButton() {
        TextButton highscoresButton = new TextButton("SCORES", uiSkin);
        highscoresButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                highscores();
            }
        });
        return highscoresButton;
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

    private void addButtonListener(boolean eMUnlocked, TextButton button) {
        if (eMUnlocked) {
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    endlessMode();
                }
            });
        } else {
            button.setStyle(uiSkin.get("locked", TextButton.TextButtonStyle.class));
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    endlessModeLocked();
                }
            });
        }
    }

    private void levels() {
        game.setScreen(new LevelsScreen(game));
    }

    private void endlessMode() {
        game.setScreen(new EndlessModeScreen(game));
    }

    private void endlessModeLocked() {
        final Dialog dialog = new Dialog("", uiSkin) {
            public void result(Object object) {

            }
        };
        Label label = new Label("Finish levels first", uiSkin);
        label.setStyle(uiSkin.get("small", Label.LabelStyle.class));

        dialog.text(label).getButtonTable().defaults().pad(20f);
        dialog.button("OK");
        dialog.center();
        dialog.show(stage);
    }

    private void highscores() {
        game.setScreen(new HighscoresScreen(game));
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
