package com.mygdx.game.ads;

import com.badlogic.gdx.utils.Logger;
import com.google.android.gms.ads.AdRequest;

public class AdUtils {

    private static final Logger log = new Logger(AdUtils.class.getSimpleName(), Logger.DEBUG);

    public static AdRequest buildRequest() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("869d76256fcdae165862720ddb8343f9").build();
        log.debug("adRequest " + adRequest.getContentUrl());
        return adRequest;
    }

    private AdUtils() {}
}
