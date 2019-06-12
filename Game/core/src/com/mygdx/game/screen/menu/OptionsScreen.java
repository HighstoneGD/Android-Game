package com.mygdx.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.Constants;
import com.mygdx.game.common.GameConfigs;

public class OptionsScreen extends MenuScreenBase {

    private TextureAtlas uiAtlas;
    private Skin uiSkin;

    private CheckBox music;
    private CheckBox sounds;

    public OptionsScreen(AndroidGame game) {
        super(game);
        uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();
        table.defaults().pad(20f);

        TextureRegion background = uiAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(background));

        Label label = new Label("OPTIONS", uiSkin);

        music = createCheckBox("Music", uiSkin);
        sounds = createCheckBox("Sounds", uiSkin);

        music.getCells().get(0).size(Constants.CHECKBOX_SIZE, Constants.CHECKBOX_SIZE);

        boolean musicOn = GameConfigs.INSTANCE.isMusicOn();
        boolean soundsOn = GameConfigs.INSTANCE.isSoundsOn();

        music.setChecked(musicOn);
        sounds.setChecked(soundsOn);

        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameConfigs.INSTANCE.setMusicOn(music.isChecked());
            }
        });

        sounds.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameConfigs.INSTANCE.setSoundsOn(sounds.isChecked());
            }
        });

        TextButton back = createBackButton(uiSkin);

        table.add(label).row();
        table.add(music).width(Constants.MENU_BUTTON_WIDTH).height(Constants.MENU_BUTTON_HEIGHT).row();
        table.add(sounds).width(Constants.MENU_BUTTON_WIDTH).height(Constants.MENU_BUTTON_HEIGHT).row();
        table.add(back).width(Constants.MENU_BUTTON_WIDTH).height(Constants.MENU_BUTTON_HEIGHT);

        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private static CheckBox createCheckBox(String text, Skin uiSkin) {
        CheckBox checkBox = new CheckBox(text, uiSkin);
        checkBox.left().pad(8f);
        checkBox.getLabelCell().pad(8);
        return checkBox;
    }

    private TextButton createBackButton(Skin uiSkin) {
        final TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });
        return backButton;
    }

    private void back() {
        game.setScreen(new MenuScreen(game));
    }
}
