package com.mygdx.game.debug;

public class GameConfig {

    public static final float WIDTH = 480f; // pixels
    public static final float HEIGHT = 800f; // pixels

    public static final float WORLD_WIDTH = 60f; // world units
    public static final float WORLD_HEIGHT = 100f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float CELL_HEIGHT = 3.3f;
    public static final float PLAYER_SIZE = 7f;

    public static final int PLAYER_START_POSITION = 12;
    public static final int START_LIVES_AMOUNT = 3;

    public static final float DEFAULT_POT_SPAWN_SPEED = 0.5f;
    public static final float SHARD_EXISTANCE_TIME = 0.4f;

    public static final int SIMPLE_CENTRAL_DAMAGE = 3;
    public static final int TRIPLE_CENTRAL_DAMAGE = 3;
    public static final int LARGE_CENTRAL_DAMAGE = 3;
    public static final int EXPLOSIVE_CENTRAL_DAMAGE = 3;
    public static final int IRON_CENTRAL_DAMAGE = 4;
    public static final int CAT_CENTRAL_DAMAGE = 1;
    public static final int SHARD_DAMAGE = 1;

    private GameConfig() {}
}
