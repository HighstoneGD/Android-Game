package com.mygdx.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.screen.game.LevelsScreen;
import com.mygdx.game.util.objects.Tutorial;

public class TutorialScreen extends MenuScreenBase {

    private static final Logger log = new Logger(TutorialScreen.class.getSimpleName(), Logger.DEBUG);

    private PotType type;

    public TutorialScreen(AndroidGame game, PotType type) {
        super(game);
        this.type = type;
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureRegion background = uiSkin.getRegion(RegionNames.MENU_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(background));

        Tutorial tutorial = new Tutorial(type, assetManager.get(AssetDescriptors.TUTORIALS));

        TextButton okButton = createOkButton();

        Table buttonTable = new Table();
        buttonTable.defaults().pad(20);
        buttonTable.add(okButton);
        buttonTable.center();

        table.add(tutorial).row();
        table.add(buttonTable);
        table.defaults().pad(20f);
        table.setFillParent(true);
        table.center();
        table.pack();

        return table;
    }

    private TextButton createOkButton() {
        TextButton button = new TextButton("OK", uiSkin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ok();
            }
        });
        return button;
    }

    private void ok() {
        GameManager.INSTANCE.setTutorialAccomplished(true);
        game.setScreen(new LevelsScreen(game));
    }
}
