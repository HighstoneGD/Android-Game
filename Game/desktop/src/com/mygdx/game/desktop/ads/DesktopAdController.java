package com.mygdx.game.desktop.ads;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.util.interfaces.AdController;

public class DesktopAdController implements AdController {

    private static final Logger log = new Logger(DesktopAdController.class.getSimpleName(), Logger.DEBUG);

    @Override
    public void showRewardedInterstitial() {
        log.debug("show rwrdbl interstitial");
    }

    @Override
    public void showBanner() {
        log.debug("show banner");
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}
