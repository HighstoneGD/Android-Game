package com.mygdx.game.common;

public class GameData {

    // screen and world size

    public static final float WIDTH = 480f; // pixels
    public static final float HEIGHT = 800f; // pixels

    public static final float WORLD_WIDTH = 60f; // world units
    public static final float WORLD_HEIGHT = 100f; // world units

    public static final float HUD_WIDTH = 480f;
    public static final float HUD_HEIGHT = 800f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    // menu elements` size

    public static final float MENU_BUTTON_WIDTH = 240f;
    public static final float MENU_BUTTON_HEIGHT = MENU_BUTTON_WIDTH / 14f * 5f;
    public static final float TITLE_SIZE = 300f;
    public static final float CHECKBOX_SIZE = 100f;

    // hud elements` size

    public static final float[] HEARTS_POSITIONS = {90, 190, 290, 390};
    public static final float HEART_SIZE = 70f;

    // gameplay elements` size

    public static final float CELL_HEIGHT = 3.3f;
    public static final float MAX_SHADOW_SIZE = 3f;
    public static final float BONUS_SIZE = 15f;

    // grid constants

    public static final float GRID_TOP = 54f;
    public static final float GRID_TOP_WIDTH = 40f;
    public static final float Y_COEFFICIENT = 1.15f;
    public static final float X_COEFFICIENT = 1.09f;
    public static final float SIZE_COEFFICIENT = 1.1f;
    public static final float POT_SIZE_COEFFICIENT = 0.95f;

    // time attributes

    public static final float POT_THROW_TIME = 0.8f;
    public static final float POT_FLIGHT_TIME = 1f;

    public static final float PLAYER_JUMP_TIME = 0.5f;

    public static final float DEFAULT_POT_SPAWN_SPEED = 1f;

    public static final float POT_EXISTENCE_TIME = 0.1f;
    public static final float BONUS_EXISTENCE_TIME = 10f;

    public static final float GRAN_FRAME_TIME = 0.1f;
    public static final float PLAYER_FRAME_TIME = PLAYER_JUMP_TIME / 8;
    public static final float FRAME_TIME = 0.1f;
    public static final float SMASH_FRAME_TIME = POT_EXISTENCE_TIME / 1.5f;

    public static final float SPEED_UP_TIME = 5f;
    public static final float ARMOR_TIME = 6f;

    // level characteristics

    public static final int POTS_AMOUNT_FIRST = 50;
    public static final int POTS_AMOUNT_SECOND = 100;
    public static final int START_LIVES_AMOUNT = 3;

    // target algorithm attributes

    public static final int CHANCE_FOR_RANDOM_TARGET = 5;

    // pots` damage

    public static final int SIMPLE_CENTRAL_DAMAGE = 3;
    public static final int LARGE_CENTRAL_DAMAGE = 3;
    public static final int EXPLOSIVE_CENTRAL_DAMAGE = 3;
    public static final int IRON_CENTRAL_DAMAGE = 4;
    public static final int SHARD_DAMAGE = 1;
    public static final int BONUS_DAMAGE = 3;

    // pots` cooldowns

    public static final int LARGE_COOLDOWN = 8;
    public static final int EXPLOSIVE_COOLDOWN = 6;
    public static final int IRON_COOLDOWN = 4;
    public static final int BONUS_COOLDOWN = 7;

    // pots` animations size

    public static final float SIMPLE_POT_SIZE = 15f;
    public static final float SIMPLE_SMASH_SIZE = 25f;

    public static final float IRON_POT_SIZE = 8f;
    public static final float IRON_SMASH_SIZE = 7.5f;

    public static final float LARGE_POT_SIZE = 22f;
    public static final float LARGE_SMASH_SIZE = 25f;

    public static final float BONUS_POT_SIZE = 8f;
    public static final float BONUS_SMASH_SIZE = 12f;

    public static final float EXPLOSIVE_POT_SIZE = 12f;
    public static final float EXPLOSIVE_SMASH_SIZE = 35f;

    // gran`s animation size and position

    public static final float GRAN_X = 30f;
    public static final float GRAN_Y = 70f;
    public static final float GRAN_SIZE = 30f;

    // player size

    public static final float PLAYER_SIZE = 50f;

    private GameData() {}
}
