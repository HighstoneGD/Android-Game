package com.mygdx.game.controlling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameConfigs;
import com.mygdx.game.common.enums.PotType;

import java.util.Random;

public class SoundsManager {

    private AssetManager assetManager;

    private Music music;

    public SoundsManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_music.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
    }

    public void playSmashSound(PotType type) {
        if (!GameConfigs.INSTANCE.isSoundsOn()) {
            return;
        }

        if (type == PotType.EXPLOSIVE) {
            assetManager.get(AssetDescriptors.EXPLOSION).play();
        } else if (type == PotType.IRON) {
            assetManager.get(AssetDescriptors.IRON_POT_SMASH).play(2f);
        } else {
            assetManager.get(AssetDescriptors.CLAY_POT_SMASH_FIRST).play();
        }
    }

    public void playMusic() {
        if (GameConfigs.INSTANCE.isMusicOn()) {
            music.play();
        }
    }

    public void stopMusic() {
        if (music.isPlaying()) {
            music.stop();
        }
    }

    public void dispose() {
        music.dispose();
    }
}
