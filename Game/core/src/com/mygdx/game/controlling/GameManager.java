package com.mygdx.game.controlling;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    public static final HealthManager HEALTH_MANAGER = new HealthManager();
    public static final CooldownsManager COOLDOWNS_MANAGER = new CooldownsManager();
    public static final AvoidedPotsManager AVOIDED_POTS_MANAGER = new AvoidedPotsManager();

    private GameManager() {}
}