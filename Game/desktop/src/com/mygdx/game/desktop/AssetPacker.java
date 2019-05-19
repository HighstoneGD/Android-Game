package com.mygdx.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 8192;
        settings.maxHeight = 8192;
        settings.debug = false;
        settings.useIndexes = true;

        TexturePacker.process(settings,
                RAW_ASSET_PATH,
                ASSETS_PATH + "/gameplay",
                "simple_pot"
        );
    }
}
