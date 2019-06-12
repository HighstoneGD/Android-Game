package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.AndroidGame;

public class GameConfigs {

    public static final GameConfigs INSTANCE = new GameConfigs();

    private static final String MUSIC_KEY = "music";
    private static final String SOUNDS_KEY = "sounds";

    private Preferences PREFS;

    private boolean musicOn;
    private boolean soundsOn;

    private GameConfigs() {
        PREFS = Gdx.app.getPreferences(AndroidGame.class.getSimpleName());
        musicOn = PREFS.getBoolean(MUSIC_KEY, true);
        soundsOn = PREFS.getBoolean(SOUNDS_KEY, true);
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public boolean isSoundsOn() {
        return soundsOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
        PREFS.putBoolean(MUSIC_KEY, musicOn);
        PREFS.flush();
    }

    public void setSoundsOn(boolean soundsOn) {
        this.soundsOn = soundsOn;
        PREFS.putBoolean(SOUNDS_KEY, soundsOn);
        PREFS.flush();
    }
}
