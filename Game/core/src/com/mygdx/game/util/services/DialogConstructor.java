package com.mygdx.game.util.services;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.screen.game.EndlessModeScreen;
import com.mygdx.game.screen.game.LevelsScreen;
import com.mygdx.game.screen.menu.PlayScreen;

public class DialogConstructor {

    public static Dialog createLvlCompleteDialog(AndroidGame game) {
        Skin skin = game.getAssetManager().get(AssetDescriptors.UI_SKIN);

        Dialog dialog = new Dialog("", skin);
        Label label = new Label("LEVEL COMPLETE!", skin);
        label.setStyle(skin.get("small", Label.LabelStyle.class));
        dialog.defaults().pad(20f);
        dialog.text(label).getButtonTable().defaults().pad(20f);

        Button homeButton = homeButtonLevels(skin, game);
        Button nextButton = nextButton(skin, game);

        dialog.button(nextButton);
        dialog.button(homeButton);
        dialog.center();

        return dialog;
    }

    public static Dialog createGameOverDialog(AndroidGame game, BasicGameScreen screen) {
        Skin uiSkin = game.getAssetManager().get(AssetDescriptors.UI_SKIN);

        Dialog dialog = new Dialog("", uiSkin);
        Label label = new Label("YOU LOST", uiSkin);
        label.setStyle(uiSkin.get("small", Label.LabelStyle.class));
        dialog.defaults().pad(20f);
        dialog.text(label).getButtonTable().defaults().pad(20f);

        Button repeatButton = repeatButton(uiSkin, game);
        Button adButton = adButton(uiSkin, screen);
        Button homeButton = homeButtonEndless(uiSkin, game);

        dialog.button(repeatButton);
        dialog.button(adButton);
        dialog.button(homeButton);
        dialog.center();

        return dialog;
    }

    private static Button repeatButton(Skin uiSkin, AndroidGame game) {
        Button repeatButton = new Button();
        repeatButton.setStyle(uiSkin.get("repeat", Button.ButtonStyle.class));
        repeatButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                repeat(game);
            }
        });
        return repeatButton;
    }

    private static Button adButton(Skin uiSkin, BasicGameScreen screen) {
        Button adButton = new Button();
        adButton.setStyle(uiSkin.get("ad", Button.ButtonStyle.class));
        adButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                watchAd(screen);
            }
        });
        return adButton;
    }

    private static Button homeButtonEndless(Skin uiSkin, AndroidGame game) {
        Button homeButton = new Button();
        homeButton.setStyle(uiSkin.get("home", Button.ButtonStyle.class));
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goHomeFromEndless(game);
            }
        });
        return homeButton;
    }

    private static Button homeButtonLevels(Skin uiSkin, AndroidGame game) {
        final Button homeButton = new Button();
        homeButton.setStyle(uiSkin.get("home", Button.ButtonStyle.class));
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goHomeFromLevels(game);
            }
        });
        return homeButton;
    }

    private static Button nextButton(Skin uiSkin, AndroidGame game) {
        Button nextButton = new Button();
        nextButton.setStyle(uiSkin.get("next", Button.ButtonStyle.class));
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goNext(game);
            }
        });
        return nextButton;
    }

    private static TextButton okButton(Skin uiSkin, Dialog dialog) {
        TextButton okButton = new TextButton("OK", uiSkin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ok(dialog);
            }
        });
        return okButton;
    }

    private static void goHomeFromEndless(AndroidGame game) {
        game.setScreen(new PlayScreen(game));
    }

    private static void goHomeFromLevels(AndroidGame game) {
        GameManager.INSTANCE.levelComplete();
        game.setScreen(new PlayScreen(game));
    }

    private static void watchAd(BasicGameScreen screen) {
        screen.watchAd();
    }

    private static void repeat(AndroidGame game) {
        if (game.getScreen().getClass() == EndlessModeScreen.class) {
            game.setScreen(new EndlessModeScreen(game));
        } else {
            game.setScreen(new LevelsScreen(game));
        }

    }

    private static void goNext(AndroidGame game) {
        GameManager.INSTANCE.levelComplete();
        game.setScreen(new LevelsScreen(game));
    }

    private static void ok(Dialog dialog) {
        dialog.hide();
    }
}
