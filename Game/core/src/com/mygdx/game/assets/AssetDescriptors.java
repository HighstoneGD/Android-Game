package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_BG =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY_BACKGROUND, TextureAtlas.class);

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);

    public static final AssetDescriptor<Sound> FUMAR_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.FUMAR_SOUND, Sound.class);

    public static final AssetDescriptor<TextureAtlas> GRAN_LOADING_ANIMATION =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GRAN_LOADING_ANIMATION, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> LOADING_BACKGROUND =
            new AssetDescriptor<TextureAtlas>(AssetPaths.LOADING_BACKGROUND, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SIMPLE_TEXTURE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SIMPLE_POT_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SIMPLE_SMASH =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SIMPLE_SMASH_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> IRON_TEXTURE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.IRON_POT_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> IRON_SMASH =
            new AssetDescriptor<TextureAtlas>(AssetPaths.IRON_SMASH_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> LARGE_TEXTURE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.LARGE_POT_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> LARGE_SMASH =
            new AssetDescriptor<TextureAtlas>(AssetPaths.LARGE_SMASH_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> BONUS_TEXTURE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.BONUS_POT_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> BONUS_SMASH =
            new AssetDescriptor<TextureAtlas>(AssetPaths.BONUS_SMASH_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> EXPLOSIVE_TEXTURE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.EXPLOSIVE_POT_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> EXPLOSIVE_SMASH =
            new AssetDescriptor<TextureAtlas>(AssetPaths.EXPLOSIVE_SMASH_TEXTURE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GRAN_SIMPLE_THROW =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GRAN_SIMPLE_THROW, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GRAN_IRON_THROW =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GRAN_IRON_THROW, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GRAN_LARGE_THROW =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GRAN_LARGE_THROW, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GRAN_EXPLOSIVE_THROW =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GRAN_EXPLOSIVE_THROW, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> PLAYER_LEFT_JUMP =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLAYER_LEFT_JUMP, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> PLAYER_RIGHT_JUMP =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLAYER_RIGHT_JUMP, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> PLAYER_VERTICAL_JUMP =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLAYER_VERTICAL_JUMP, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> HUD =
            new AssetDescriptor<TextureAtlas>(AssetPaths.HUD, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> STATIC =
            new AssetDescriptor<TextureAtlas>(AssetPaths.STATIC, TextureAtlas.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<TextureAtlas> UI_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.UI_ATLAS, TextureAtlas.class);

    private AssetDescriptors() {
    }
}
