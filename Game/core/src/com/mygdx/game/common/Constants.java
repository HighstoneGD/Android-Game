package com.mygdx.game.common;

public class Constants {

    public static final float WIDTH = 480f; // pixels
    public static final float HEIGHT = 800f; // pixels

    public static final float HUD_WIDTH = 480f; // wu
    public static final float HUD_HEIGHT = 800f; // wu

    public static final float WORLD_WIDTH = 60f; // world units
    public static final float WORLD_HEIGHT = 100f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float CELL_HEIGHT = 3.3f;
    public static final float PLAYER_SIZE = 7f;

    public static final int START_LIVES_AMOUNT = 3;

    public static final float DEFAULT_POT_SPAWN_SPEED = 0.7f;
    public static final float DEFAULT_BONUS_SPAWN_SPEED = 10f;
    public static final int CHANCE_FOR_RANDOM_TARGET = 5;

    public static final int SIMPLE_CENTRAL_DAMAGE = 3;
    public static final int LARGE_CENTRAL_DAMAGE = 3;
    public static final int EXPLOSIVE_CENTRAL_DAMAGE = 3;
    public static final int IRON_CENTRAL_DAMAGE = 4;
    public static final int CAT_DAMAGE = 2;
    public static final int SHARD_DAMAGE = 1;
    public static final int BONUS_DAMAGE = 3;

    public static final float SIMPLE_EXISTANCE_TIME = 0.3f;
    public static final float SHARD_EXISTANCE_TIME = 0.3f;
    public static final float LARGE_EXISTANCE_TIME = 0.3f;
    public static final float EXPLOSIVE_EXISTANCE_TIME = 0.6f;
    public static final float IRON_EXISTANCE_TIME = 0.15f;
    public static final float CAT_STAY_TIME = 0.5f;
    public static final float BONUS_EXISTANCE_TIME = 10f;

    public static final long SIMPLE_FLIGHT_TIME = 500;
    public static final long LARGE_FLIGHT_TIME = 500;
    public static final long EXPLOSIVE_FLIGHT_TIME = 500;
    public static final long IRON_FLIGHT_TIME = 200;
    public static final long CAT_FLIGHT_TIME = 500;
    public static final long CAT_JUMP_TIME = 500;
    public static final long PLAYER_JUMP_TIME = 300;
    public static final long BONUS_FLIGHT_TIME = 500;

    public static final int TRIPLE_COOLDOWN = 6;
    public static final int LARGE_COOLDOWN = 6;
    public static final int EXPLOSIVE_COOLDOWN = 6;
    public static final int IRON_COOLDOWN = 6;
    public static final int CAT_COOLDOWN = 6;

    public static final int SPEED_COOLDOWN = 10;
    public static final int ARMOR_COOLDOWN = 20;
    public static final int TIME_DECELERATION_COOLDOWN = 50;
    public static final int LIFE_COOLDOWN = 100;

    public static final float GRID_TOP = 54f;
    public static final float GRID_TOP_WIDTH = 40f;
    public static final float Y_COEFFICIENT = 1.15f;
    public static final float X_COEFFICIENT = 1.09f;
    public static final float SIZE_COEFFICIENT = 1.05f;

    private Constants() {}
}
