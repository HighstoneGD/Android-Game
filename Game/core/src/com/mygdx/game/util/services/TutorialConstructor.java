package com.mygdx.game.util.services;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.enums.PotType;
import com.mygdx.game.controlling.GameManager;
import com.mygdx.game.screen.game.BasicGameScreen;
import com.mygdx.game.screen.menu.TutorialScreen;

public class TutorialConstructor {

    public static final TutorialConstructor INSTANCE = new TutorialConstructor();

    private static final Logger log = new Logger(TutorialConstructor.class.getSimpleName(), Logger.DEBUG);

    private AndroidGame game;

    public void handle(int lvl, AndroidGame game) {
        this.game = game;
        if (!GameManager.INSTANCE.isTutorialAccomplished()) {
            createTutorial(lvl);
        }
    }

    private void createTutorial(int lvl) {
        if (lvl == 1) {
            changeToTutorialScreen(PotType.SIMPLE);
        } else if (lvl == 3) {
            changeToTutorialScreen(PotType.LARGE);
        } else if (lvl == 4) {
            changeToTutorialScreen(PotType.BONUS);
        } else if (lvl == 5) {
            changeToTutorialScreen(PotType.EXPLOSIVE);
        } else if (lvl == 6) {
            changeToTutorialScreen(PotType.IRON);
        } else {
            GameManager.INSTANCE.setTutorialAccomplished(true);
        }
    }

    private void changeToTutorialScreen(PotType type) {
        game.setScreen(new TutorialScreen(game, type));
    }

}
