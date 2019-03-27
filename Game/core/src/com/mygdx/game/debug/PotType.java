package com.mygdx.game.debug;

import com.badlogic.gdx.utils.Logger;

public enum PotType {
    SIMPLE(GameConfig.SIMPLE_CENTRAL_DAMAGE),
    TRIPLE(GameConfig.TRIPLE_CENTRAL_DAMAGE),
    LARGE(GameConfig.LARGE_CENTRAL_DAMAGE),
    EXPLOSIVE(GameConfig.EXPLOSIVE_CENTRAL_DAMAGE),
    IRON(GameConfig.IRON_CENTRAL_DAMAGE),
    CAT(GameConfig.CAT_CENTRAL_DAMAGE),
    SHARD(GameConfig.SHARD_DAMAGE);

    private final int damage;

    private static final Logger log = new Logger(PotType.class.getName(), Logger.DEBUG);

    PotType(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        log.debug("damage = " + damage);
        return damage;
    }
}
