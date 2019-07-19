package com.mygdx.game;

import com.mygdx.game.screen.loading.LoadingScreen;
import com.mygdx.game.util.interfaces.AdController;

public class AndroidGame extends GameBase {

    public AndroidGame(AdController adController) {
        super(adController);
    }

    @Override
    public void postCreate() {
        setScreen(new LoadingScreen(this));
//        getAdController().showBanner();
    }
}
