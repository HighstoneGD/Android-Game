package com.mygdx.game;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mygdx.game.ads.AdUtils;
import com.mygdx.game.database.DatabaseManager;

public class AndroidLauncher extends AndroidApplication implements AdController {

    private static final Logger log = new Logger(AndroidLauncher.class.getSimpleName(), Logger.DEBUG);

    private AndroidGame game;
    private DatabaseManager databaseManager;

    private AdView bannerAdView;
    private View gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        initAds();
        initGame();
        initUi();
        initDBManager();
    }

    private void initDBManager() {
        databaseManager = new DatabaseManager(game);
    }

    private void initAds() {
        bannerAdView = new AdView(this);
        bannerAdView.setAdSize(AdSize.SMART_BANNER);
//        MobileAds.initialize(this, getResources().getString(R.string.banner_id));
        bannerAdView.setAdUnitId(getResources().getString(R.string.banner_id));

    }

    private void initGame() {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new AndroidGame(this);
        gameView = initializeForView(game, config);
    }

    private void initUi() {
        RelativeLayout layout = new RelativeLayout(this);

//        RelativeLayout.LayoutParams adParams = createAdParams();
        RelativeLayout.LayoutParams gameParams = createGameParams();

        layout.addView(gameView, gameParams);
//        layout.addView(bannerAdView, adParams);

        setContentView(layout);
    }

//    private RelativeLayout.LayoutParams createAdParams() {
//        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//
//        return adParams;
//    }

    private RelativeLayout.LayoutParams createGameParams() {
        return new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bannerAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        bannerAdView.pause();
    }

    @Override
    protected void onDestroy() {
//        bannerAdView.destroy();
        super.onDestroy();
    }

    @Override
    public void showBanner() {
        log.debug("show banner()");
        runOnUiThread(this::loadBanner);
    }

    private void loadBanner() {
        if (isNetworkConnected()) {
            log.debug("load banner()");
            bannerAdView.loadAd(AdUtils.buildRequest());
        }
    }

    @Override
    public void showRewardedInterstitial() {

    }

    @Override
    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            return true;
        } catch (NullPointerException e) {
            return false;
        }

//        NetworkInfo networkCallback = connectivityManager.getActiveNetworkInfo();
//        return networkCallback != null && networkCallback.isConnected();
    }
}
