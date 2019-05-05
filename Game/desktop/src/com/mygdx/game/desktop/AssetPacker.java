package com.mygdx.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        settings.debug = false;
        settings.useIndexes = false;

        TexturePacker.process(settings,
                RAW_ASSET_PATH,
                ASSETS_PATH + "/ui",
                "uiskin"
        );
    }
}
