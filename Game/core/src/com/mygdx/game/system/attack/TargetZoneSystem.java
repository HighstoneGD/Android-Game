package com.mygdx.game.system.attack;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.debug.PotType;

import java.util.HashMap;
import java.util.Map;

public class TargetZoneSystem extends EntitySystem {

    private static final Logger log = new Logger(TargetZoneSystem.class.getName(), Logger.DEBUG);

    private static Map<Integer, PotType> zone = new HashMap<Integer, PotType>();

    public TargetZoneSystem() {}

    public Map<Integer, PotType> attack() {
        PotType type = getEngine().getSystem(PotSpawnSystem.class).selectPotType();
        int cell = getEngine().getSystem(TargetSystem.class).selectTargetCell();

        switch (type) {
            case SIMPLE: return simpleAttack(cell);
        }
    }

    private Map<Integer, PotType> simpleAttack(int cell) {
        Map<Integer, PotType> map = new HashMap<Integer, PotType>();
        map.put(cell, PotType.SIMPLE);

        if (cell > 4) {
            map.put(cell - 5, PotType.SHARD);
        }

        if (cell < 20) {
            map.put(cell + 5, PotType.SHARD);
        }

        if (cell % 5 != 0) {
            map.put(cell - 1, PotType.SHARD);
        }

        if (cell % 5 != 4) {
            map.put(cell + 1, PotType.SHARD);
        }

        return map;
    }

    private Map<Integer, PotType> largeAttack(int cell) {
        Map<Integer, PotType> map = new HashMap<Integer, PotType>();
        map.put(cell, PotType.LARGE);

        if ()

        return map;
    }
}
